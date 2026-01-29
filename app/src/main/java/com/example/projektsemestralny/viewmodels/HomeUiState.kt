package com.example.projektsemestralny.viewmodels

import android.location.Location
import com.example.projektsemestralny.data.ActivitySession

/**
 * Prosty UiState dla ekranu głównego oraz szczegółów.
 * Zawiera wszystkie dane potrzebne do renderowania UI.
 */
data class HomeUiState(
    val stepCount: Int = 0,
    val location: Location? = null,
    val sessions: List<ActivitySession> = emptyList(),
    val totalSteps: Int = 0,
    val dailySteps: Map<String, Int> = emptyMap()
)



