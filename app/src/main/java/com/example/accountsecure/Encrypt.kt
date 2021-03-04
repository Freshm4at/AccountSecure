package com.example.accountsecure

import android.annotation.SuppressLint
import android.util.Base64
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec


class Encrypt {

    fun encrypt(data: String, password : String) : String{
        val key = generateKey(password)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE,key)
        val encVal = cipher.doFinal(data.toByteArray())
        return Base64.encodeToString(encVal,Base64.DEFAULT)
    }

    fun generateKey(password: String): SecretKeySpec  {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes : ByteArray = password.toByteArray()
        digest.update(bytes,0,bytes.size)
        val key : ByteArray = digest.digest()
        return SecretKeySpec(key,"AES")
    }

    @SuppressLint("GetInstance")
    fun decrypt(outputString : String, password : String) : String{
        val key = generateKey(password)
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.DECRYPT_MODE,key)
        val decodeValue = Base64.decode(outputString,Base64.DEFAULT)
        val decValue = cipher.doFinal(decodeValue)
        return String(decValue)

    }
}