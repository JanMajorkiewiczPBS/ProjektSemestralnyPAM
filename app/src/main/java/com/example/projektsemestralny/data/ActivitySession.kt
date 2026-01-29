package com.example.projektsemestralny.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_sessions")
data class ActivitySession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val stepCount: Int,
    val latitude: Double,
    val longitude: Double,
    val imagePath: String? // Path to the saved image
)
