package com.example.unittestwithmockk.domain

/**
 * Created by Thoai Nguyen on 12/10/25.
 */
import androidx.biometric.BiometricManager

class AuthChecker(
    private val biometricManager: BiometricManager
) {
    fun hasBiometric(): Boolean {
        val res = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG
        )
        return res == BiometricManager.BIOMETRIC_SUCCESS
    }

    fun hasDeviceCredential(): Boolean {
        val res = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.DEVICE_CREDENTIAL
        )
        return res == BiometricManager.BIOMETRIC_SUCCESS
    }
}