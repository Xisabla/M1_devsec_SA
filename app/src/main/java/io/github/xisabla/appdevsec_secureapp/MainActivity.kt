package io.github.xisabla.appdevsec_secureapp

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

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    private var service: ApiService? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set Application title
        title = "Accounts";

        // Set retrofit call url
        // TODO: Read url from somewhere else ("Api url is recoverable (-2pts)")
        service = retrofit.baseUrl("https://6007f1a4309f8b0017ee5022.mockapi.io/api/m1/")
            .build()
            .create(ApiService::class.java)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "M1_devsec_SA_test1.db"
        )
            .allowMainThreadQueries()
            .build()

        showAccountsFromDB()
        refreshAccounts()
    }

    private fun showAccountsFromDB() {
        val accounts = db.accountDao().getAll()
        var accountText = ""

        // Append text
        for (account in accounts) {
            accountText += "Account Name: " + account.name + "\n" + "Account IBAN: " + account.iban + "\n" + "Amount: " + account.amount + account.currency + "\n\n"
        }

        // Update TextView text
        findViewById<TextView>(R.id.testTextView).apply {
            text = "Listing " + accounts.size.toString() + " accounts:\n\n" + accountText
        }
    }

    fun refreshAccounts(view: View) {
        findViewById<TextView>(R.id.testTextView).apply {
            text = "Refreshing..."
        }

        val accountsRequest = service?.getAccountInformation()

        accountsRequest?.enqueue(object : Callback<List<AccountsByID>> {
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
                        accountsList.get(account).accountName,
                        accountsList.get(account).accountAmount.toString(),
                        accountsList.get(account).accountCurrency,
                        accountsList.get(account).accountIban
                    )

                    if (db.accountDao().getByName(acc.name) != null) {
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
}