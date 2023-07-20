package com.ics342.labs

<<<<<<< HEAD
import android.content.res.Resources
=======
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
>>>>>>> 79c3d67ddfa26188fb634a2283c260195dbc09e0
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
=======
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
>>>>>>> 79c3d67ddfa26188fb634a2283c260195dbc09e0
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
<<<<<<< HEAD
import androidx.compose.ui.unit.dp
=======
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
>>>>>>> 79c3d67ddfa26188fb634a2283c260195dbc09e0
import com.ics342.labs.ui.theme.LabsTheme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.Type

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonData = loadData(resources)
        val data = dataFromJsonString(jsonData)
        setContent {
            var hasPermission // state for tracking if the permission has been granted
            var showPermissionRationale // state for tracking if the rationale should be shown
            val context = LocalContext.current

            val launcher = // The ManagedActivityResultLauncher for handling requesting permission

            LabsTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
<<<<<<< HEAD
                    LazyColumn {
                        items(data) { person ->
                            Text(
                                text = "${person.id}, ${person.giveName} ${person.familyName}, Age: ${person.age}",
                                modifier = Modifier.padding(16.dp)
                            )
                        }
=======
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        // if permission has been granted, show the LocationView
                        // else if permission has not been granted, show a button to to request permission

                        // --------

                        // if user has denied permission and we should show the rationale, show the dialog
>>>>>>> 79c3d67ddfa26188fb634a2283c260195dbc09e0
                    }
                }
            }
        }
    }
}

<<<<<<< HEAD

private fun loadData(resources: Resources): String {
    return resources
        .openRawResource(R.raw.data)
        .bufferedReader()
        .use { it.readText() }
}


@OptIn(ExperimentalStdlibApi::class)
private fun dataFromJsonString(json: String): List<Person> {
    val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    val jsonAdapter : JsonAdapter<List<Person>> = moshi.adapter()
    return jsonAdapter.fromJson(json) ?: listOf()
=======
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
    // Ask Android if the app has the permission with ContextCompat.checkSelfPermission

    // if permission is granted, call the permission granted function
    // if permission is not granted, launch the launcher for the permission
>>>>>>> 79c3d67ddfa26188fb634a2283c260195dbc09e0
}
