package com.talkfrly.multiplatform.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

fun initAndroidDataStore(context: Context): DataStore<Preferences> =
    initDataStore(
        producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
    )