package io.github.xisabla.appdevsec_secureapp

import android.util.Log
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.lang.StringBuilder

class PasswordManagement() {
    companion object {
        /**
         * @return Indicate if a new file has been created or not
         */
        fun checkFileExist(appContext: File): String{
            val file = File(appContext, "password")
            val returnExist=file.exists()
            if (returnExist.toString()=="false") {
                val fileWriter = FileWriter(file)
                fileWriter.write("39DFA55283318D31AFE5A3FF4A0E3253E2045E43")
                fileWriter.close()
                return "File has been created"
            }
            return "File already exist"
        }
        /**
         * @return The stored hashed password of the Application
         */
        fun getStoredPwHash(appContext: File): String {
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
        fun setStoredPwdHash(appContext: File, newPassHash : String): String {
            val file = File(appContext, "password")
            val fileWriter = FileWriter(file)
            fileWriter.write(newPassHash)
            fileWriter.close()
            return "Password has been changed !"
        }
    }
}