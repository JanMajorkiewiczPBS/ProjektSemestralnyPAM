package com.example.projektsemestralny.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.projektsemestralny.data.ActivitySession
import com.example.projektsemestralny.data.AppDatabase
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(application: Application) : AndroidViewModel(application), SensorEventListener {

    private val db = AppDatabase.getDatabase(application)
    private val activitySessionDao = db.activitySessionDao()

    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val stepCounterSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> = _stepCount

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location

    private val _allSessions = MutableStateFlow<List<ActivitySession>>(emptyList())
    val allSessions: StateFlow<List<ActivitySession>> = _allSessions.asStateFlow()

    private val _totalStepCount = MutableStateFlow(0)
    val totalStepCount: StateFlow<Int> = _totalStepCount.asStateFlow()

    private val _dailySteps = MutableStateFlow<Map<String, Int>>(emptyMap())
    val dailySteps: StateFlow<Map<String, Int>> = _dailySteps.asStateFlow()

    // UiState używany przez ekrany Compose
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val last = locationResult.lastLocation
            _location.value = last
            _uiState.update { current ->
                current.copy(location = last)
            }
        }
    }

    init {
        viewModelScope.launch {
            activitySessionDao.getAllSessions().collect { sessions ->
                _allSessions.value = sessions
                _totalStepCount.value = sessions.sumOf { session -> session.stepCount }
                _dailySteps.value = sessions.groupBy { session ->
                    val cal = Calendar.getInstance()
                    cal.timeInMillis = session.timestamp
                    "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}"
                }.mapValues { entry -> entry.value.sumOf { session -> session.stepCount } }

                // Zaktualizuj UiState na podstawie nowych danych z bazy
                _uiState.update { current ->
                    current.copy(
                        sessions = sessions,
                        totalSteps = _totalStepCount.value,
                        dailySteps = _dailySteps.value
                    )
                }
            }
        }
    }

    fun saveActivitySession(imagePath: String?) {
        viewModelScope.launch {
            val session = ActivitySession(
                timestamp = System.currentTimeMillis(),
                stepCount = _stepCount.value,
                latitude = _location.value?.latitude ?: 0.0,
                longitude = _location.value?.longitude ?: 0.0,
                imagePath = imagePath
            )
            activitySessionDao.insertSession(session)
            // Reset kroków po zapisaniu sesji
            resetStepCount()
        }
    }

    fun resetStepCount() {
        _stepCount.value = 0
        _uiState.update { current ->
            current.copy(stepCount = 0)
        }
    }

    fun incrementStepCount() {
        _stepCount.value += 1
        val newValue = _stepCount.value
        _uiState.update { current ->
            current.copy(stepCount = newValue)
        }
    }

    fun deleteAllSessions() {
        viewModelScope.launch {
            activitySessionDao.deleteAllSessions()
        }
    }

    fun startStepCounting() {
        if (stepCounterSensor != null) {
            sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val newValue = event.values[0].toInt()
            _stepCount.value = newValue
            _uiState.update { current ->
                current.copy(stepCount = newValue)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for this implementation
    }

    override fun onCleared() {
        super.onCleared()
        sensorManager.unregisterListener(this)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
