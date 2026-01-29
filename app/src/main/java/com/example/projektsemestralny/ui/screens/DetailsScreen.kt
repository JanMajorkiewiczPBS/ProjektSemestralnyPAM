package com.example.projektsemestralny.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.projektsemestralny.viewmodels.HomeViewModel
import java.io.File

@Composable
fun DetailsScreen(viewModel: HomeViewModel) {
    
    val uiState by viewModel.uiState.collectAsState()
    val sessions = uiState.sessions
    val totalStepCount = uiState.totalSteps
    val dailySteps = uiState.dailySteps

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Statistics(totalStepCount, dailySteps)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { viewModel.deleteAllSessions() }) {
                Text(text = "Delete All Sessions")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Proste udostępnianie danych przez e-mail / inne aplikacje
                val summary = buildString {
                    appendLine("Total steps: ${uiState.totalSteps}")
                    appendLine("Sessions count: ${sessions.size}")
                    appendLine()
                    appendLine("Recent sessions:")
                    sessions.take(5).forEach { session ->
                        appendLine(
                            "Steps=${session.stepCount}, " +
                                "lat=${session.latitude}, lon=${session.longitude}"
                        )
                    }
                }
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Activity summary")
                    putExtra(Intent.EXTRA_TEXT, summary)
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share via"))
            }) {
                Text(text = "Share summary")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Recent Sessions", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }

        items(sessions) { session ->
            Card(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Timestamp: ${session.timestamp}")
                    Text(text = "Steps: ${session.stepCount}")
                    Text(text = "Location: ${session.latitude}, ${session.longitude}")
                    session.imagePath?.let {
                        Image(
                            painter = rememberAsyncImagePainter(File(it)),
                            contentDescription = null,
                            modifier = Modifier
                                .size(128.dp)
                                .padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Statistics(totalSteps: Int, dailySteps: Map<String, Int>) {
    Column {
        Text("Total Steps: $totalSteps", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Daily Steps Chart", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        BarChart(dailySteps)
    }
}

@Composable
fun BarChart(data: Map<String, Int>) {
    val maxSteps = data.values.maxOrNull() ?: 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        data.forEach { (day, steps) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .fillMaxHeight(fraction = steps.toFloat() / maxSteps)
                        .background(MaterialTheme.colorScheme.primary)
                )
                Text(text = day, fontSize = 10.sp)
            }
        }
    }
}
