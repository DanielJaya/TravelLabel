package com.example.travellabel.View.Signup

import android.content.Intent
import retrofit2.HttpException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.travellabel.R
import com.example.travellabel.Response.RegisterResponse
import com.example.travellabel.View.Login.LoginActivity
import com.example.travellabel.ViewModelFactory
import com.example.travellabel.databinding.ActivitySignupBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        submitSignup()
    }

    private fun submitSignup() {
        binding.signupButton.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString().trim()
            val username = binding.edRegisterUsername.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = getString(R.string.erroremail)
            } else if (username.isEmpty()) {
                binding.usernameEditTextLayout.error = getString(R.string.errorusername)
            } else if (password.isEmpty()) {
                binding.passwordEditTextLayout.error = getString(R.string.errorpassword)
            } else {
                binding.emailEditTextLayout.error = null
                binding.usernameEditTextLayout.error = null
                binding.passwordEditTextLayout.error = null

                viewModel.register(email, username, password,
                    onResult = { response ->
                        showToast(response?.message)
                        MaterialAlertDialogBuilder(this)
                            .setTitle(getString(R.string.messageTitle))
                            .setMessage(getString(R.string.messageRegister))
                            .setPositiveButton(getString(R.string.messageButton)) { _, _ ->
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }
                            .show()
                    },
                    onError = { errorMessage ->
                        showToast(errorMessage)
                    }
                )
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}