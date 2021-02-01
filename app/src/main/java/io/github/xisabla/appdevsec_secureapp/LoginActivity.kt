package io.github.xisabla.appdevsec_secureapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Login Activity: Asks the user to prompt a numeric pin to unlock the main activity
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "Accounts: login"
    }

    /**
     * @return The stored hashed password of the Application
     */
    private fun getStoredPasswordHash() : String {
        // Todo: fetch the password hash from a file
        return "39DFA55283318D31AFE5A3FF4A0E3253E2045E43"
    }

    /**
     * @return True if the given password is valid to unlock the Application
     */
    private fun checkPassword(password: String): Boolean {
        // Compute password hash
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(password.toByteArray(Charset.defaultCharset()))
        val sb = StringBuilder()
        result.forEach { b -> sb.append(String.format("%02X", b)) }

        val hash = sb.toString()

        // TODO: Remove debug stuff
        Log.d("Given Password", password)
        Log.d("Password hash", hash)

        // Check password hash
        return hash.toUpperCase() == getStoredPasswordHash().toUpperCase()
    }

    /**
     * Attempt to login the Application, if the good password is given in the password field,
     * will launch the Main Activity (which means, unlock the Application)
     */
    fun login(view: View) {
        val input = findViewById<EditText>(R.id.passwordInput)
        val errorMessage = findViewById<TextView>(R.id.errorMessage)
        val password = input.text.toString()

        // Reset pin and text
        input.text.clear()
        errorMessage.visibility = View.INVISIBLE

        // Launch MainActivity if the password is good
        if(checkPassword(password)) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Show error message
            errorMessage.visibility = View.VISIBLE
        }
    }
}