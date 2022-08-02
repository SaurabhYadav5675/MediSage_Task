package com.medisage.meditask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.medisage.meditask.appDatabase.UserDatabase
import com.medisage.meditask.databinding.ActivitySignupBinding
import com.medisage.meditask.model.User
import com.medisage.meditask.utilities.FieldValidation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding;
    lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = UserDatabase.getDatabase(this)

        binding.signupBtn.setOnClickListener {
            val userName = binding.signupName.text
            val userContact = binding.signupContact.text
            val userMail = binding.signupMail.text
            val userPass = binding.signupPassword.text

            val isValidName =
                FieldValidation.isValidName(userName.toString(), binding.nameContainer)

            val isValidContact =
                FieldValidation.isValidContact(userContact.toString(), binding.contactContainer)

            val isValidEmail =
                FieldValidation.isValidEmail(userMail.toString(), binding.nameContainer)

            val isValidPass =
                FieldValidation.isValidPassword(userPass.toString(), binding.nameContainer)

            if ((isValidName && isValidContact) && (isValidEmail && isValidPass)) {
                GlobalScope.launch {
                    database.userDao()
                        .insertUser(
                            User(
                                0,
                                userName.toString(),
                                userContact.toString(),
                                userMail.toString(),
                                userPass.toString()
                            )
                        )
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}