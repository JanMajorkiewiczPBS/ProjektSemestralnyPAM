package com.example.projektsemestralny.ui.screens

import android.Manifest
import android.graphics.Bitmap
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projektsemestralny.utils.saveBitmapToFile
import com.example.projektsemestralny.viewmodels.HomeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToDetails: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val stepCount = uiState.stepCount
    val location = uiState.location
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newBitmap ->
            bitmap = newBitmap
        }
    )

    val permissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.CAMERA
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        permissions.add(Manifest.permission.ACTIVITY_RECOGNITION)
    }

    val permissionStates = rememberMultiplePermissionsState(permissions = permissions)

    LaunchedEffect(permissionStates) {
        if (permissionStates.allPermissionsGranted) {
            viewModel.startStepCounting()
            viewModel.startLocationUpdates()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (permissionStates.allPermissionsGranted) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Step Count: $stepCount")
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Location: ${location?.latitude}, ${location?.longitude}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { cameraLauncher.launch() }) {
                    Text(text = "Take Photo")
                }
                Spacer(modifier = Modifier.height(16.dp))
                bitmap?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val imagePath = bitmap?.let { saveBitmapToFile(context, it) }
                    viewModel.saveActivitySession(imagePath)
                    bitmap = null
                }) {
                    Text(text = "Save Session")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.incrementStepCount() }) {
                    Text(text = "+1 Step (Test)")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onNavigateToDetails) {
                    Text(text = "View Saved Sessions")
                }
            }
        } else {
            Button(onClick = { permissionStates.launchMultiplePermissionRequest() }) {
                Text(text = "Request Permissions")
            }
        }
    }
}
