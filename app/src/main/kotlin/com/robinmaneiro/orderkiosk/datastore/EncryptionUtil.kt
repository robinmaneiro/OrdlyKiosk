package com.robinmaneiro.orderkiosk.datastore

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.RegistryConfiguration
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlin.io.encoding.Base64

object EncryptionUtil {
    private const val KEYSET_NAME = "tink_keyset"
    private const val PREF_FILE_NAME = "secure_prefs"
    private var aead: Aead? = null

    fun initialize(context: Context) {
        TinkConfig.register()
        val keysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri("android-keystore://tink_master_key")
            .build()
            .keysetHandle

        aead = keysetHandle.getPrimitive(RegistryConfiguration.get(), Aead::class.java)
    }

    fun encrypt(plainText: String): String {
        val cipherBytes = aead?.encrypt(plainText.toByteArray(), null) ?: return ""
        return Base64.Default.encode(cipherBytes)
    }

    fun decrypt(encryptedText: String): String {
        val cipherBytes = Base64.Default.decode(encryptedText)
        val plainBytes = aead?.decrypt(cipherBytes, null) ?: return ""
        return String(plainBytes)
    }
}