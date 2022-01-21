package com.example.ghostzilla.database.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.BLOCK_MODE_GCM
import android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE
import android.security.keystore.KeyProperties.KEY_ALGORITHM_AES
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.util.Base64
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject

class SecurityUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val provider = "AndroidKeyStore"

    private val cipher by lazy { Cipher.getInstance("AES/GCM/NoPadding") }

    private val charset by lazy { charset("UTF-8") }

    private val keyStore by lazy { KeyStore.getInstance(provider).apply { load(null) } }

    private val keyGenerator by lazy { KeyGenerator.getInstance(KEY_ALGORITHM_AES, provider) }

    private val ivStorage = context.getSharedPreferences("IV", Context.MODE_PRIVATE)

    fun encryptData(keyAlias: String, text: String): ByteArray {
        cipher.init(Cipher.ENCRYPT_MODE, generateSecretKey(keyAlias))
        ivStorage.edit { putString("IV", Base64.encodeToString(cipher.iv, Base64.DEFAULT)) }
        return cipher.doFinal(text.toByteArray(charset))
    }

    fun decryptData(keyAlias: String, encryptedData: ByteArray): String {
        cipher.apply {
            init(
                Cipher.DECRYPT_MODE,
                getSecretKey(keyAlias),
                GCMParameterSpec(
                    128,
                    Base64.decode(
                        requireNotNull(ivStorage.getString("IV", "Fuck")),
                        Base64.DEFAULT
                    )
                )
            )
        }
        return cipher.doFinal(encryptedData).toString(charset)
    }

    private fun generateSecretKey(keyAlias: String): SecretKey {
        return keyGenerator.apply {
            init(
                KeyGenParameterSpec
                    .Builder(keyAlias, PURPOSE_ENCRYPT or PURPOSE_DECRYPT)
                    .setBlockModes(BLOCK_MODE_GCM)
                    .setEncryptionPaddings(ENCRYPTION_PADDING_NONE)
                    .build()
            )
        }.generateKey()
    }

    private fun getSecretKey(keyAlias: String) =
        (keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry).secretKey

    private fun String.decodeByteArray(): ByteArray {
        val splittedBytes = this.substring(1, this.length - 1).split(", ")
        val decodedByteArray = ByteArray(splittedBytes.size)
        decodedByteArray.indices.map { decodedByteArray[it] = splittedBytes[it].toByte() }
        return decodedByteArray
    }
}