package com.example.accountsecure


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import okhttp3.*
import java.util.*
import java.util.concurrent.Executor


class Login : AppCompatActivity() {
    companion object { var _id: Int? = 0
        init {
            System.loadLibrary("keys")
        }
        external fun getAPIKey(): String
        external fun getPwKey(): String
    }
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this@Login,executor,
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Retry or enter a password...", Toast.LENGTH_SHORT).show()
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    login()
                }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric authentification")
            .setSubtitle("Login using fingerprint authentification")
            .setNegativeButtonText("use app Password instead")
            .build()

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            if (findViewById<EditText>(R.id.editTextTextPassword).text.toString() == getPwKey()) {
                login()
            }else{biometricPrompt.authenticate(promptInfo)}
        }
        findViewById<ImageView>(R.id.wow_portal).setOnClickListener {
            if (findViewById<EditText>(R.id.editTextTextPassword).text.toString() == getPwKey()) {
                login()
            }else{biometricPrompt.authenticate(promptInfo)}
        }
    }

    private fun login(){
        val loginId = findViewById<EditText>(R.id.etxt_pw).text.toString().toIntOrNull()
        if(loginId!=null){
            _id = loginId
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
        }else{
            val toast = Toast.makeText(applicationContext, "Enter an Id!", Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
