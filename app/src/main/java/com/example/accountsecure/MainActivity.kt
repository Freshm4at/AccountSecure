package com.example.accountsecure

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.RCAccounts).layoutManager = LinearLayoutManager(this)
        fetch()
        findViewById<Button>(R.id.btn_disconnect).setOnClickListener {
            Login._id=0
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btn_refresh).setOnClickListener {
            fetch()
            Toast.makeText(applicationContext, "Refreshed", Toast.LENGTH_SHORT).show()
        }

    }

    private fun fetch(){
        val request = Request.Builder()
                .url(Base64.decode(Login.getAPIKey(), Base64.DEFAULT).decodeToString()+Login._id)
                .build()
        val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build()

        val client = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .certificatePinner(
                        CertificatePinner.Builder()
                                .add("mockapi.io", "sha256/a60d6f05f1a27d3dfe6f43a7822ddd752b7e0964")
                                .build())
                .build()
        client.newCall(request).enqueue(object : Callback {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call, e: IOException) {
                try {
                    val jsonfile = Encrypt().decrypt(applicationContext.assets.open("account${Login._id}.txt").bufferedReader().use { it.readText() }, Login.getPwKey())
                    val gson = GsonBuilder().create()
                    val account = gson.fromJson(jsonfile, Account::class.java)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Offline", Toast.LENGTH_SHORT).show()
                        findViewById<TextView>(R.id.txt_status).text = "Offline"
                        val bank = BankAccounts(mutableListOf(account))
                        findViewById<RecyclerView>(R.id.RCAccounts).adapter = CustomAdapter(bank)
                    }
                }catch (e: FileNotFoundException){
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Can't find this account, retry another", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (response.code != 404) {
                    val gson = GsonBuilder().create()
                    val account = gson.fromJson(body, Account::class.java)
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Online", Toast.LENGTH_SHORT).show()
                        findViewById<TextView>(R.id.txt_status).text = "Updated"
                        val bank = BankAccounts(mutableListOf(account))
                        findViewById<RecyclerView>(R.id.RCAccounts).adapter = CustomAdapter(bank)
                    }
                }
                else{
                    runOnUiThread {
                        Toast.makeText(applicationContext, "Can't find this account, retry another", Toast.LENGTH_LONG).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}