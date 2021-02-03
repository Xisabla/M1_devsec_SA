package io.github.xisabla.appdevsec_secureapp.model

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import io.github.xisabla.appdevsec_secureapp.R

class PinChangeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_change)

        title = "Change Pin"
    }

    /**
     * will verify if both new passwords are the same and react accordingly
     */
    private fun verifyPassword(newPassword: String, check_newPassword: String) {
        if (newPassword == check_newPassword) {
            //todo change password in file

            /**
             * displays a dynamic message telling user whether the change has been done
             */
            val myToast = Toast.makeText(
                applicationContext,
                "changed password successfully",
                Toast.LENGTH_SHORT
            )
            myToast.show()

            finish()
        } else {
            val myToast =
                Toast.makeText(applicationContext, "Passwords do not match", Toast.LENGTH_SHORT)
            myToast.show()
        }
    }

    private fun changePassword() {
        /**
         * first password change variable
         */
        val input = findViewById<EditText>(R.id.editPassword)

        /**
         * second password change variable
         */
        val input2 = findViewById<EditText>(R.id.editPassword2)

        val newPassword = input.text.toString()
        val newPassword2 = input2.text.toString()

        verifyPassword(newPassword, newPassword2)
    }

    /**
     * allows the verify Password button to call the function
     */
    fun changePassword(view: View){
        return changePassword()
    }
    /**
     * When clicking the goBack button, the user will return to the account page
     */
    fun goBack(view: View) {
        finish()
    }
}