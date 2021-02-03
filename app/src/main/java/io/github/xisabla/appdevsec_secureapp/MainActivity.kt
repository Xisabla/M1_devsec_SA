package io.github.xisabla.appdevsec_secureapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.room.Room
import io.github.xisabla.appdevsec_secureapp.api.AccountsByID
import io.github.xisabla.appdevsec_secureapp.api.ApiService
import io.github.xisabla.appdevsec_secureapp.database.AppDatabase
import io.github.xisabla.appdevsec_secureapp.model.Account
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Main Activity: Allow the user to see his accounts and to refresh the data
 */
class MainActivity : AppCompatActivity() {

    /**
     * Accounts database, defined later
     */
    private lateinit var db: AppDatabase

    /**
     * API Call builder
     */
    private val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    /**
     * Retrofit API Service, defined later
     */
    private lateinit var service: ApiService;

    /**
     * Fetch the API Call url
     */
    private fun getAPIUrl() : String {
        // Todo: fetch the url from a file (cyphered ?, encrypted file, whatever)
        return "https://6007f1a4309f8b0017ee5022.mockapi.io/api/m1/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set Application title
        title = "Accounts";

        // Set retrofit call url
        service = retrofit.baseUrl(getAPIUrl())
            .build()
            .create(ApiService::class.java)

        // Build the database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "M1_devsec_SA.db"
        )
            .allowMainThreadQueries()
            .build()

        // Show stored data and try to refresh
        showAccountsFromDB()
        refreshAccounts()
    }

    /**
     * Update textview with the database accounts information
     */
    private fun showAccountsFromDB() {
        val accounts = db.accountDao().getAll()
        var accountText = ""

        // Append text
        for (account in accounts) {
            accountText += "Account Name: " + account.name + "\n" + "Account IBAN: " + account.iban + "\n" + "Amount: " + account.amount + account.currency + "\n\n"
        }

        // Update TextView text
        findViewById<TextView>(R.id.accountsText).apply {
            text = "Listing " + accounts.size.toString() + " accounts:\n\n" + accountText
        }
    }

    /**
     * Refresh accounts data from API call
     */
    private fun refreshAccounts() {
        findViewById<TextView>(R.id.accountsText).apply {
            text = "Refreshing..."
        }

        val accountsRequest = service.getAccountInformation()

        accountsRequest.enqueue(object : Callback<List<AccountsByID>> {
            // Throw on failure
            override fun onFailure(call: Call<List<AccountsByID>>, t: Throwable) {
                t.message?.let { error(it) }
            }

            // Process on response
            override fun onResponse(
                call: Call<List<AccountsByID>>,
                response: Response<List<AccountsByID>>
            ) {
                val accountsList = mutableListOf<AccountsByID>()

                // Fetch accounts
                for (x in 0 until (response.body()?.size!!)) response.body()?.get(x)
                    ?.let { accountsList.add(it) }

                // Update/Insert to Database
                for (account in 0 until (accountsList.size)) {
                    val acc = Account(
                        accountsList[account].accountName,
                        accountsList[account].accountAmount.toString(),
                        accountsList[account].accountCurrency,
                        accountsList[account].accountIban
                    )

                    if(db.accountDao().exists(acc.name)) {
                        db.accountDao().update(acc)
                    } else {
                        db.accountDao().insert(acc)
                    }
                }

                // Update show
                showAccountsFromDB()
            }

        })


    }

    /**
     * Refresh accounts from button
     */
    fun refreshAccounts(view: View) {
        return refreshAccounts()
    }

    /**
     * Launch password changer activity
     */
    fun openPinChanger(view: View) {
        startActivity(Intent(this, PinChangeActivity::class.java))
    }

    /**
     * Lock the application by finishing the activity (going back to login activity)
     */
    fun lock(view: View) {
        finish()
    }

}