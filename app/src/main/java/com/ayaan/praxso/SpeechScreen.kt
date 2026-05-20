package com.ayaan.praxso

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

@Composable
fun SpeechScreen(
    navController: NavHostController, modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }

    var currentTarget by remember {
        mutableStateOf<String?>(null)
    }

    var retryCount by remember {
        mutableStateOf(0)
    }

    // -----------------------------------------
    // Prevent multiple recognizer calls
    // -----------------------------------------

    var isListening by remember {
        mutableStateOf(false)
    }

    // -----------------------------------------
    // Permission
    // -----------------------------------------

    var hasPermission by remember {

        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->

        hasPermission = granted

        if (!granted) {

            Toast.makeText(
                context, "Microphone permission denied", Toast.LENGTH_SHORT
            ).show()
        }
    }

    // -----------------------------------------
    // Speech Recognizer
    // -----------------------------------------

    val speechRecognizer = remember {

        SpeechRecognizer.createSpeechRecognizer(
            context.applicationContext
        )
    }

    val speechIntent = remember {

        Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        ).apply {

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault().toLanguageTag()
            )

            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                Locale.getDefault().toLanguageTag()
            )

            putExtra(
                RecognizerIntent.EXTRA_MAX_RESULTS, 1
            )

            putExtra(
                RecognizerIntent.EXTRA_PARTIAL_RESULTS, false
            )
        }
    }

    // -----------------------------------------
    // Start Listening
    // -----------------------------------------

    val startListening: (String) -> Unit = { target ->

        // prevent double clicks
        if (isListening) {

            Toast.makeText(
                context, "Already listening...", Toast.LENGTH_SHORT
            ).show()

        } else if (!hasPermission) {

            launcher.launch(
                Manifest.permission.RECORD_AUDIO
            )

        } else if (!SpeechRecognizer.isRecognitionAvailable(context)) {

            Toast.makeText(
                context, "Speech recognition unavailable", Toast.LENGTH_SHORT
            ).show()

        } else {

            currentTarget = target
            retryCount = 0

            try {

                isListening = true

                speechRecognizer.cancel()

                speechRecognizer.startListening(
                    speechIntent
                )

            } catch (e: Exception) {

                isListening = false
                currentTarget = null

                Log.e(
                    "SpeechSession", "Recognizer exception", e
                )

                Toast.makeText(
                    context, "Failed to start speech recognition", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // -----------------------------------------
    // Recognition Listener
    // -----------------------------------------

    val listener = remember {

        object : RecognitionListener {

            override fun onReadyForSpeech(params: Bundle?) {

                Log.d(
                    "SpeechSession", "Ready for speech"
                )
            }

            override fun onBeginningOfSpeech() {

                Log.d(
                    "SpeechSession", "Speech started"
                )
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {

                Log.d(
                    "SpeechSession", "Speech ended"
                )

                isListening = false
            }

            override fun onError(error: Int) {

                isListening = false

                val errorMessage = when (error) {

                    SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"

                    SpeechRecognizer.ERROR_CLIENT -> "Client error"

                    SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Permission denied"

                    SpeechRecognizer.ERROR_NETWORK -> "Network error"

                    SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"

                    SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized"

                    SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognizer busy"

                    SpeechRecognizer.ERROR_SERVER -> "Server error"

                    SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected"

                    else -> "Unknown error"
                }

                Log.e(
                    "SpeechSession", "Speech Error: $error -> $errorMessage"
                )

                Toast.makeText(
                    context, errorMessage, Toast.LENGTH_SHORT
                ).show()

                val target = currentTarget

                if (error == SpeechRecognizer.ERROR_CLIENT || error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {

                    if (target != null && retryCount == 0) {

                        retryCount = 1

                        Handler(
                            Looper.getMainLooper()
                        ).postDelayed({

                            if (currentTarget == target) {
                                startListening(target)
                            }
                        }, 400)

                        return
                    }
                }

                currentTarget = null
                retryCount = 0
            }

            override fun onResults(results: Bundle?) {

                isListening = false

                val matches = results?.getStringArrayList(
                    SpeechRecognizer.RESULTS_RECOGNITION
                )

                val recognizedText = matches?.firstOrNull() ?: ""

                Log.d(
                    "SpeechSession", "Recognized: $recognizedText"
                )

                when (currentTarget) {

                    "name" -> {
                        name = recognizedText
                    }

                    "age" -> {

                        age = recognizedText.filter {
                            it.isDigit()
                        }

                        if (age.isBlank()) {
                            age = recognizedText
                        }
                    }
                }

                currentTarget = null
                retryCount = 0
            }

            override fun onPartialResults(
                partialResults: Bundle?
            ) {
            }

            override fun onEvent(
                eventType: Int, params: Bundle?
            ) {
            }
        }
    }

    // -----------------------------------------
    // Attach listener
    // -----------------------------------------

    DisposableEffect(Unit) {

        speechRecognizer.setRecognitionListener(
            listener
        )

        onDispose {

            speechRecognizer.stopListening()
            speechRecognizer.cancel()
            speechRecognizer.destroy()
        }
    }


    // -----------------------------------------
    // UI
    // -----------------------------------------

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "User Registration", style = MaterialTheme.typography.headlineMedium,

            modifier = Modifier.padding(bottom = 32.dp)
        )

        // -----------------------------------------
        // Name
        // -----------------------------------------

        OutlinedTextField(
            value = name,

            onValueChange = {
                name = it
            },

            label = {
                Text("Name")
            },

            modifier = Modifier.fillMaxWidth(),

            trailingIcon = {

                IconButton(
                    enabled = !isListening,

                    onClick = {
                        startListening("name")
                    }) {

                    Icon(
                        imageVector = Icons.Default.Mic, contentDescription = "Speak Name"
                    )
                }
            })

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // -----------------------------------------
        // Age
        // -----------------------------------------

        OutlinedTextField(
            value = age,

            onValueChange = {
                age = it
            },

            label = {
                Text("Age")
            },

            modifier = Modifier.fillMaxWidth(),

            trailingIcon = {

                IconButton(
                    enabled = !isListening,

                    onClick = {
                        startListening("age")
                    }) {

                    Icon(
                        imageVector = Icons.Default.Mic, contentDescription = "Speak Age"
                    )
                }
            })

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // -----------------------------------------
        // Submit
        // -----------------------------------------

        Button(
            modifier = Modifier.fillMaxWidth(),

            onClick = {

                if (name.isBlank() || age.isBlank()) {

                    Toast.makeText(
                        context, "Please enter all fields", Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                val userMap = hashMapOf(
                    "name" to name, "age" to age
                )

                db.collection("users").add(userMap)

                    .addOnSuccessListener {

                        Toast.makeText(
                            context, "Saved successfully", Toast.LENGTH_SHORT
                        ).show()

                        name = ""
                        age = ""
                    }

                    .addOnFailureListener { e ->

                        Toast.makeText(
                            context, "Firestore Error: ${e.message}", Toast.LENGTH_LONG
                        ).show()
                    }
            }) {

            Text("Submit to Firestore")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                navController.popBackStack()
            }) {

            Text("Go Back")
        }
    }
}