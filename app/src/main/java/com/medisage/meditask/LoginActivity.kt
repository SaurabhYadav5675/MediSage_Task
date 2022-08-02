package com.medisage.meditask

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.medisage.meditask.appDatabase.UserDatabase
import com.medisage.meditask.databinding.ActivityLoginBinding
import com.medisage.meditask.utilities.AppPreference
import com.medisage.meditask.utilities.FieldValidation

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = UserDatabase.getDatabase(this)

        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.loginBtn.setOnClickListener {

            val userEmail = binding.loginEmail.text
            val userPass = binding.loginPassword.text

            val isValidEmail =
                FieldValidation.isValidEmail(userEmail.toString(), binding.emailContainer)
            val isValidPass =
                FieldValidation.isValidPassword(userPass.toString(), binding.passwordContainer)


            if (isValidEmail && isValidPass) {

                database.userDao().getUser(userEmail.toString(), userPass.toString())
                    .observe(this@LoginActivity,
                        Observer {
                            if (it != null && it.isNotEmpty()) {
                                AppPreference.setLoginStatus(this, 1)
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this,
                                    "Invalid username or password.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })
            }
        }
    }
}