package com.talkfrly.multiplatform.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.person
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.round

@Composable
fun AccountScreenRoot(
    viewModel: AccountViewModel = koinViewModel(),
    navController: NavController,
    onLogout: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.onIntent(AccountIntent.GetUser)
        viewModel.onIntent(AccountIntent.GetUserPreferences)
        viewModel.onIntent(AccountIntent.RefreshImageCacheStats)
    }

    AccountScreen(
        state = state,
//        navController = navController,
        onBackClick = { navController.popBackStack() },
        onAction = { intent ->
            viewModel.onIntent(intent, onLogoutSuccess = { onLogout() })
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AccountScreen(
    state: AccountState,
//    navController: NavController,
    onBackClick: () -> Unit,
    onAction: (AccountIntent) -> Unit,
) {

    var selected by remember { mutableStateOf("B") }

    var expanded by remember { mutableStateOf(false) }
    val options = listOf("English", "Polski")
    var DpMenuSelected by remember { mutableStateOf(options.first()) }

    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                    actionIconContentColor = LocalTalkfrlyColors.current.body,
                ),
                navigationIcon = {
                    IconButton(
//                        onClick = { navController.popBackStack() },
                        onClick = { onBackClick() },
                        enabled = true,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = null,
                        )
                    }
                },
//                actions = {
//                    if (navController.currentBackStackEntry?.destination?.route == Route.Account.id) {
//                        IconButton(
//                            onClick = { navController.navigate(Route.Account.id) },
//                            enabled = true,
//                        ) {
//                            Icon(
//                                imageVector = vectorResource(Res.drawable.person),
//                                contentDescription = null,
//                            )
//                        }
//                    }
//                }
            )
        },
    ) { innerPadding ->
        Column( modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(vertical = 16.dp),
                    text = "Info",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    if(state.user?.avatarUrl != null){
                        AsyncImage(
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                                .border(2.dp, LocalTalkfrlyColors.current.primary60,
                                    RoundedCornerShape(8.dp)
                                ),
                            model = state.user.avatarUrl,
                            contentDescription = null,

                        )
                    }else{
                        Image(
                            modifier = Modifier
                                .height(100.dp)
                                .width(100.dp)
                                .border(2.dp, LocalTalkfrlyColors.current.primary60,
                                    RoundedCornerShape(8.dp)
                                ),
                            imageVector = vectorResource(Res.drawable.person),
                            contentDescription = null,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.user?.let {
                            Column {
                                Text(
                                    text = "EMAIL",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(state.user.email)
                            }

                            Column {
                                Text(
                                    text = "DISPLAY NAME",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(state.user.displayName)
                            }

                            Column {
                                Text(
                                    text = "USER ID",
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                )
                                Text(state.user.id)
                            }
                        }
                    }
                }

            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.DarkGray,
            )
            Column {
                Text(
                    text = "Preferences",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Choose how your name appears on posts and comments",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selected == "A",
                            onClick = { selected = "A" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalTalkfrlyColors.current.primary60
                            )
                        )
                        Text("Show display name (" + state.user?.displayName + ")")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selected == "B",
                            onClick = { selected = "B" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalTalkfrlyColors.current.primary60
                            )
                        )
                        Text("Show user ID")
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selected == "C",
                            onClick = { selected = "C" },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = LocalTalkfrlyColors.current.primary60
                            )
                        )
                        Text("Always anonymous (hides everything)")
                    }
                }
                Text(
                    modifier  = Modifier.padding(vertical = 12.dp),
                    text = "DISPLAY NAME",
                    fontSize = 16.sp,
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    value = state.userNameInput,
                    onValueChange = { onAction(AccountIntent.SetUserName(it)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = LocalTalkfrlyColors.current.backgroundDarker,
                        focusedContainerColor = LocalTalkfrlyColors.current.backgroundDarker,
                        unfocusedBorderColor = LocalTalkfrlyColors.current.primary60,
                        focusedBorderColor = LocalTalkfrlyColors.current.primary,
                        unfocusedTextColor = LocalTalkfrlyColors.current.body,
                        focusedTextColor = LocalTalkfrlyColors.current.body,
                    )
                )
                Text(
                    text = "Your display name shown in posts and comments",
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Text(
                    modifier  = Modifier.padding(vertical = 12.dp),
                    text = "APP LANGUAGE",
                    fontSize = 16.sp,
                )
                ExposedDropdownMenuBox(
                    modifier = Modifier.padding(vertical = 4.dp),
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                ) {
                    OutlinedTextField(
                        modifier = Modifier.menuAnchor(),
                        value = DpMenuSelected,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = LocalTalkfrlyColors.current.body,
                            focusedTextColor = LocalTalkfrlyColors.current.body,
                            unfocusedBorderColor = LocalTalkfrlyColors.current.primary60,
                            focusedBorderColor = LocalTalkfrlyColors.current.primary60
                        )
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.background(LocalTalkfrlyColors.current.background),
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(option, color = LocalTalkfrlyColors.current.body) },
                                onClick = {
                                    DpMenuSelected = option
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Text(
                    text = "Choose the language for the app interface. The page will reload.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Button(
                    modifier = Modifier.padding(vertical = 8.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalTalkfrlyColors.current.primary,
                        contentColor = LocalTalkfrlyColors.current.black
                    )
                ){
                    Text("Save preferences")
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.DarkGray,
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Image Cache",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "RAM: ${formatBytes(state.memoryCacheSizeBytes)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Disk: ${formatBytes(state.diskCacheSizeBytes)}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Button(
                    modifier = Modifier.padding(top = 8.dp),
                    onClick = { onAction(AccountIntent.ClearImageCache) },
                    enabled = !state.isClearingImageCache,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalTalkfrlyColors.current.primary,
                        contentColor = LocalTalkfrlyColors.current.black
                    )
                ) {
                    Text(
                        if (state.isClearingImageCache) {
                            "Clearing image cache..."
                        } else {
                            "Clear image cache"
                        }
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp,
                color = Color.DarkGray,
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { onAction(AccountIntent.Logout) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = LocalTalkfrlyColors.current.backgroundDarker,
                    contentColor = LocalTalkfrlyColors.current.body
                )
            ){
                Text("Logout")
            }
        }
    }
}

private fun formatBytes(bytes: Long?): String {
    if (bytes == null) return "Unavailable"
    if (bytes <= 0L) return "0 B"

    val units = listOf("B", "KB", "MB", "GB", "TB")
    val digitGroup = (ln(bytes.toDouble()) / ln(1024.0)).toInt().coerceAtMost(units.lastIndex)
    val value = bytes / 1024.0.pow(digitGroup.toDouble())

    return if (digitGroup == 0) {
        "${bytes} ${units[digitGroup]}"
    } else {
        "${round(value * 10) / 10} ${units[digitGroup]}"
    }
}