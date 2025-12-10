package com.example.unittestwithmockk.ui.updateemail

/**
 * Created by Thoai Nguyen on 12/10/25.
 */
import androidx.lifecycle.ViewModel
import com.example.unittestwithmockk.domain.AuthChecker
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class UpdateEmailViewModel(
    private val checker: AuthChecker
) : ViewModel() {

    private val _event = MutableSharedFlow<UpdateEmailEvent>()
    val event: SharedFlow<UpdateEmailEvent> = _event

    suspend fun startAuthFlow() {
        when {
            checker.hasBiometric() ->
                _event.emit(UpdateEmailEvent.ShowBiometricPrompt)

            checker.hasDeviceCredential() ->
                _event.emit(UpdateEmailEvent.ShowPasscodePrompt)

            else ->
                _event.emit(UpdateEmailEvent.ShowNoAuthDialog)
        }
    }
}

sealed class UpdateEmailEvent {
    object ShowBiometricPrompt : UpdateEmailEvent()
    object ShowPasscodePrompt : UpdateEmailEvent()
    object ShowNoAuthDialog : UpdateEmailEvent()
}
