package com.example.projektsemestralny.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivitySessionDao {

    @Insert
    suspend fun insertSession(session: ActivitySession)

    @Query("SELECT * FROM activity_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<ActivitySession>>

    @Query("DELETE FROM activity_sessions")
    suspend fun deleteAllSessions()
}
