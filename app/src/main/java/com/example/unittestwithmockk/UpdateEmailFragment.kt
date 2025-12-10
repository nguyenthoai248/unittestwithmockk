package com.example.unittestwithmockk

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.unittestwithmockk.domain.AuthChecker
import com.example.unittestwithmockk.ui.updateemail.UpdateEmailEvent
import com.example.unittestwithmockk.ui.updateemail.UpdateEmailViewModel
import kotlinx.coroutines.launch

/**
 * Created by Thoai Nguyen on 12/10/25.
 */
class UpdateEmailFragment : Fragment(R.layout.fragment_update_email) {

    private lateinit var viewModel: UpdateEmailViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val biometricManager = BiometricManager.from(requireContext())
        val checker = AuthChecker(biometricManager)
        viewModel = UpdateEmailViewModel(checker)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        is UpdateEmailEvent.ShowBiometricPrompt -> showBiometricPrompt()
                        is UpdateEmailEvent.ShowPasscodePrompt -> showPasscodePrompt()
                        is UpdateEmailEvent.ShowNoAuthDialog -> showNoAuthDialog()
                    }
                }
            }
        }

        view.findViewById<Button>(R.id.btnNext).setOnClickListener {
            lifecycleScope.launch { viewModel.startAuthFlow() }
        }
    }

    private fun showBiometricPrompt() {
        Toast.makeText(context, "Show biometric", Toast.LENGTH_SHORT).show()
    }

    private fun showPasscodePrompt() {
        Toast.makeText(context, "Show passcode", Toast.LENGTH_SHORT).show()
    }

    private fun showNoAuthDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("No Authentication")
            .setMessage("Your device does not have biometrics or passcode enabled.")
            .setPositiveButton("Open YouTube") { _, _ ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://guitarzone.vn")))
            }
            .setNegativeButton("Close", null)
            .show()
    }
}