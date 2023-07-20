package com.ics342.labs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.ics342.labs.ui.theme.LabsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var hasPermission by remember { mutableStateOf(false) } // state for tracking if the permission has been granted
            var showPermissionRationale by remember { mutableStateOf(false) } // state for tracking if the rationale should be shown
            val context = LocalContext.current

            val launcher = rememberLauncherForActivityResult(RequestPermission()) { isGranted ->
                if (isGranted) {
                    hasPermission = true
                } else {
                    showPermissionRationale = true
                }
            }

            LabsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        if (hasPermission) {
                            // if permission has been granted, show the LocationView
                            LocationView()
                        } else if (!showPermissionRationale) {
                            // else if permission has not been granted and the rationale should not be shown, show a button to request permission
                            Button(onClick = {
                                checkOrRequestPermission(
                                    context = context,
                                    permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                                    launcher = launcher,
                                    permissionGranted = { hasPermission = true }
                                )
                            }) {
                                Text("Request Permission")
                            }
                        }

                        if (showPermissionRationale) {
                            // if user has denied permission and we should show the rationale, show the dialog
                            PermissionRationaleDialog(
                                onConfirm = {
                                    showPermissionRationale = false
                                    checkOrRequestPermission(
                                        context = context,
                                        permission = Manifest.permission.ACCESS_COARSE_LOCATION,
                                        launcher = launcher,
                                        permissionGranted = { hasPermission = true }
                                    )
                                },
                                onCancel = { showPermissionRationale = false }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationView() {
    Text("Has Location Permission")
}

@Composable
private fun PermissionRationaleDialog(
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = @Composable {
            Button(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        dismissButton = @Composable {
            Button(onClick = { onCancel() }) {
                Text("No")
            }
        },
        title = @Composable {
            Text("Permissions Required")
        },
        text = @Composable {
            Text("This app requires this permission to use this feature.")
        }
    )
}

private fun checkOrRequestPermission(
    context: Context,
    permission: String,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    permissionGranted: () -> Unit
) {
    val isPermissionGranted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    if (isPermissionGranted) {
        // if permission is granted, call the permission granted function
        permissionGranted()
    } else {
        // if permission is not granted, launch the launcher for the permission
        launcher.launch(permission)
    }
}
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            var hasPermission // state for tracking if the permission has been granted
//            var showPermissionRationale // state for tracking if the rationale should be shown
//            val context = LocalContext.current
//
//            val launcher = // The ManagedActivityResultLauncher for handling requesting permission
//
//            LabsTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//
//                        // if permission has been granted, show the LocationView
//                        // else if permission has not been granted, show a button to to request permission
//
//                        // --------
//
//                        // if user has denied permission and we should show the rationale, show the dialog
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//private fun LocationView() {
//    Text("Has Location Permission")
//}
//
//@Composable
//private fun PermissionRationaleDialog(
//    onConfirm: () -> Unit,
//    onCancel: () -> Unit
//) {
//    AlertDialog(
//        onDismissRequest = {},
//        confirmButton = @Composable {
//            Button(onClick = { onConfirm() }) {
//                Text("OK")
//            }
//        },
//        dismissButton = @Composable {
//            Button(onClick = { onCancel() }) {
//                Text("No")
//            }
//        },
//        title = @Composable {
//            Text("Permissions Required")
//        },
//        text = @Composable {
//            Text("This app requires this permission to use this feature.")
//        }
//    )
//}
//
//private fun checkOrRequestPermission(
//    context: Context,
//    permission: String,
//    launcher: ManagedActivityResultLauncher<String, Boolean>,
//    permissionGranted: () -> Unit
//) {
//    // Ask Android if the app has the permission with ContextCompat.checkSelfPermission
//
//    // if permission is granted, call the permission granted function
//    // if permission is not granted, launch the launcher for the permission
//}
