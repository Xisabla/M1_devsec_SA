package io.github.xisabla.appdevsec_secureapp

import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.security.MessageDigest

/**
 * Password Management: static methods to read and write password hash to data storage
 */
class PasswordManagement() {
    companion object {
        /**
         * @return The SHA-1 hash of the given password/pin as a String
         */
        fun hash(password: String): String {
            val digest = MessageDigest.getInstance("SHA-1")
            val result = digest.digest(password.toByteArray(Charset.defaultCharset()))
            val sb = StringBuilder()
            result.forEach { b -> sb.append(String.format("%02X", b)) }

            return sb.toString()
        }


        /**
         * @return Assume that the file exists, if doesn't create it with default pin "0000"
         */
        private fun checkFileExist(appContext: File) {
            val file = File(appContext, "password")

            if(!file.exists()) {
                setStoredPwdHash(appContext, hash("0000"))
            }
        }

        /**
         * @return The stored hashed password of the Application
         */
        fun getStoredPwHash(appContext: File): String {
            checkFileExist(appContext)

            val file = File(appContext, "password")
            val fileReader = FileReader(file)
            val content = StringBuilder()

            fileReader.forEachLine { line -> content.append(line) }
            fileReader.close()

            return content.toString()
        }

        /**
         * @return Change current password by newPassHash
         */
        fun setStoredPwdHash(appContext: File, newPassHash : String) {
            val file = File(appContext, "password")
            val fileWriter = FileWriter(file)

            fileWriter.write(newPassHash)
            fileWriter.close()
        }
    }
}