package io.github.xisabla.appdevsec_secureapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import io.github.xisabla.appdevsec_secureapp.api.AccountsByID
import io.github.xisabla.appdevsec_secureapp.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit: Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())

    private var service: ApiService? = null;

    private val accountsList: MutableList<AccountsByID> = mutableListOf<AccountsByID>()

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
                // Empty current list
                // NOTE: This is temporarily, as we will use ad db the process will be the following one:
                //  - New local accountsList here
                //  - Fill it (accountsList.add)
                //  - Update database information with accountsList content
                //  - Update global accountsList from the database
                //     --> This should be in another method as it would be called at that start of the appplication as well
                //  - Update view
                accountsList.clear()

                // Fetch accounts
                for (x in 0 until (response.body()?.size!!)) response.body()?.get(x)
                    ?.let { accountsList.add(it) }

                //  This is temporarily as well, don't really mind
                var accountText = "";

                // Debug: print accounts
                // TODO: Remove this (Debugging information are accessible (-2pts))
                for (account in 0 until (accountsList.size)) {
                    accountText += "Account Name: " + accountsList.get(account).accountName + "\n" + "Account IBAN: " + accountsList.get(
                        account
                    ).accountIban + "\n" + "Account Amount: " + accountsList.get(account).accountAmount.toString() + accountsList.get(
                        account
                    ).accountCurrency + "\n\n"

                    /*Log.d("account name", accountsList.get(account).accountName)
                    Log.d("account Iban", accountsList.get(account).accountIban)
                    Log.d(
                        "account amount",
                        accountsList.get(account).accountAmount.toString() + accountsList.get(
                            account
                        ).accountCurrency
                    )*/
                }

                // Show it's done
                findViewById<TextView>(R.id.testTextView).apply {
                    text = "Done: " + accountsList.size + " accounts fetched\n\n\n" + accountText
                }
            }

        })
    }
}