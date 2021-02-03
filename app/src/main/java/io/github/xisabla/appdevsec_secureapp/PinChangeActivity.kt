package io.github.xisabla.appdevsec_secureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class PinChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_change)

        title = "Change Pin"
    }

    /**
     * Will verify if both new passwords are the same and react accordingly
     */
    private fun verifyPassword(password: String, passwordConfirm: String): Boolean {
        if (password == passwordConfirm) {
            PasswordManagement.setStoredPwdHash(
                applicationContext.filesDir,
                PasswordManagement.hash(password)
            )

            val myToast = Toast.makeText(
                applicationContext,
                "Password changed successfully",
                Toast.LENGTH_SHORT
            )
            myToast.show()

            return true
        } else {
            val myToast =
                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT)
            myToast.show()

            return false
        }
    }

    /**
     * Allows the verify Password button to call the function
     */
    fun changePassword(view: View) {
        val input = findViewById<EditText>(R.id.editPassword)
        val inputConfirm = findViewById<EditText>(R.id.editPasswordConfirm)

        val password = input.text.toString()
        val passwordConfirm = inputConfirm.text.toString()

        if (verifyPassword(password, passwordConfirm)) {
            finish()
        }
    }

    /**
     * When clicking the goBack button, the user will return to the account page
     */
    fun goBack(view: View) {
        finish()
    }
}