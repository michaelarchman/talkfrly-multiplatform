package com.talkfrly.multiplatform.ui.pickers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSUUID
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.writeToFile
import platform.PhotosUI.PHPickerConfiguration
import platform.PhotosUI.PHPickerFilter
import platform.PhotosUI.PHPickerResult
import platform.PhotosUI.PHPickerViewController
import platform.PhotosUI.PHPickerViewControllerDelegateProtocol
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.darwin.NSObject

@Composable
actual fun rememberImagePickerController(): ImagePickerController {
    return remember { IosImagePickerController() }
}

private class IosImagePickerController : ImagePickerController {
    private var onResultCallback: (ImagePickerResult) -> Unit = {}

    private var galleryDelegate: PHPickerViewControllerDelegateProtocol? = null
    private var cameraDelegate: UIImagePickerControllerDelegateProtocol? = null

    override fun onResult(block: (ImagePickerResult) -> Unit) {
        onResultCallback = block
    }

    override fun openGallery() {
        val config = PHPickerConfiguration().apply {
            filter = PHPickerFilter.imagesFilter
            selectionLimit = 1
        }

        val picker = PHPickerViewController(config)

        val delegate = object : NSObject(), PHPickerViewControllerDelegateProtocol {
            override fun picker(picker: PHPickerViewController, didFinishPicking: List<*>) {
                picker.dismissViewControllerAnimated(true, completion = null)

                val result = didFinishPicking.firstOrNull() as? PHPickerResult ?: return
                val provider = result.itemProvider

                provider.loadDataRepresentationForTypeIdentifier("public.image") { data, _ ->
                    if (data == null) return@loadDataRepresentationForTypeIdentifier
                    val fileName = "talkfrly_${NSUUID().UUIDString}.jpg"
                    val path = NSTemporaryDirectory() + fileName
                    val success = data.writeToFile(path, true)
                    if (success) {
                        onResultCallback(ImagePickerResult(listOf("file://$path")))
                    }
                }
            }
        }

        galleryDelegate = delegate
        picker.delegate = delegate

        topViewController()?.presentViewController(picker, animated = true, completion = null)
    }

    override fun openCamera() {
        val picker = UIImagePickerController().apply {
            sourceType = platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            allowsEditing = false
        }

        val delegate = object : NSObject(), UIImagePickerControllerDelegateProtocol, UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                picker.dismissViewControllerAnimated(true, completion = null)

                val image = didFinishPickingMediaWithInfo[platform.UIKit.UIImagePickerControllerOriginalImage] as? UIImage
                    ?: return
                val uri = saveImageToTemp(image)
                if (uri != null) {
                    onResultCallback(ImagePickerResult(listOf(uri)))
                }
            }

            override fun imagePickerControllerDidCancel(picker: UIImagePickerController) {
                picker.dismissViewControllerAnimated(true, completion = null)
            }
        }

        cameraDelegate = delegate
        picker.delegate = delegate

        topViewController()?.presentViewController(picker, animated = true, completion = null)
    }

    private fun saveImageToTemp(image: UIImage): String? {
        val data = UIImageJPEGRepresentation(image, 0.9) ?: return null
        val fileName = "talkfrly_${NSUUID().UUIDString}.jpg"
        val path = NSTemporaryDirectory() + fileName
        val success = data.writeToFile(path, true)
        return if (success) "file://$path" else null
    }

    private fun topViewController(): UIViewController? {
        val window: UIWindow? = UIApplication.sharedApplication.keyWindow
            ?: UIApplication.sharedApplication.windows.firstOrNull() as? UIWindow
        var top = window?.rootViewController
        while (top?.presentedViewController != null) {
            top = top.presentedViewController
        }
        return top
    }
}
