package com.example.unittestwithmockk

/**
 * Created by Thoai Nguyen on 12/10/25.
 */
import androidx.biometric.BiometricManager
import com.example.unittestwithmockk.domain.AuthChecker
import com.example.unittestwithmockk.ui.updateemail.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateEmailViewModelTest {

    @Test
    fun `when biometric available -> emit ShowBiometricPrompt`() = runTest {
        val biometricManager = mockk<BiometricManager>()
        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        } returns BiometricManager.BIOMETRIC_SUCCESS

        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

        val vm = UpdateEmailViewModel(AuthChecker(biometricManager))

        vm.startAuthFlow()
        assert(vm.event.first() is UpdateEmailEvent.ShowBiometricPrompt)
    }

    @Test
    fun `when no biometric but passcode available -> emit ShowPasscodePrompt`() = runTest {
        val biometricManager = mockk<BiometricManager>()
        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

        every {
            biometricManager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
        } returns BiometricManager.BIOMETRIC_SUCCESS

        val vm = UpdateEmailViewModel(AuthChecker(biometricManager))

        vm.startAuthFlow()
        assert(vm.event.first() is UpdateEmailEvent.ShowPasscodePrompt)
    }

    @Test
    fun `when neither biometric nor passcode available -> emit ShowNoAuthDialog`() = runTest {
        val biometricManager = mockk<BiometricManager>()
        every {
            biometricManager.canAuthenticate(any())
        } returns BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED

        val vm = UpdateEmailViewModel(AuthChecker(biometricManager))

        vm.startAuthFlow()
        assert(vm.event.first() is UpdateEmailEvent.ShowNoAuthDialog)
    }
}
