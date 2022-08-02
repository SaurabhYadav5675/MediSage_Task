package com.medisage.meditask.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class FieldValidation {
    companion object {
        private val PASSWORD_PATTERN: Pattern = Pattern.compile(
            "^" +
                    "(?=.*[@#$%^&+=])" +  // at least 1 special character
                    "(?=\\S+$)" +  // no white spaces
                    ".{8,}" +  // at least 8 characters
                    "$"
        )


        fun isInternetAvailable(context: Context): Boolean {
            (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return this.getNetworkCapabilities(this.activeNetwork)
                        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
                } else {
                    @Suppress("Deprecation")
                    return this.activeNetworkInfo?.isConnected ?: false
                }
            }
        }

        fun isValidName(name: String, nameContainer: TextInputLayout): Boolean {
            var isValid: Boolean = false
            if (TextUtils.isEmpty(name)) {
                nameContainer.error = "Please enter name"
            } else if (name.trim().isEmpty()) {
                nameContainer.error = "Invalid name"
            } else {
                nameContainer.error = ""
                isValid = true
            }
            return isValid
        }

        fun isValidContact(contact: String, contactContainer: TextInputLayout): Boolean {
            var isValid: Boolean = false
            if (TextUtils.isEmpty(contact)) {
                contactContainer.error = "Please enter contact"
            } else if (contact.length < 10) {
                contactContainer.error = "Invalid contact"
            } else {
                contactContainer.error = ""
                isValid = true
            }
            return isValid
        }

        fun isValidEmail(email: String, emailContainer: TextInputLayout): Boolean {
            var isValid: Boolean = false
            if (TextUtils.isEmpty(email)) {
                emailContainer.error = "Please enter email"
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailContainer.error = "Invalid email"
            } else {
                emailContainer.error = ""
                isValid = true
            }
            return isValid
        }

        fun isValidPassword(pass: String, passwordContainer: TextInputLayout): Boolean {
            var isValid: Boolean = false
            if (pass.length < 8 && pass.length < 15) {
                passwordContainer.error = "Password must be between 8 to 15 digits"
            } else if (!PASSWORD_PATTERN.matcher(pass).matches()) {
                passwordContainer.error = "Password is too weak"
            } else {
                passwordContainer.error = ""
                isValid = true
            }
            return isValid
        }
    }
}