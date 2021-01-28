package io.github.xisabla.appdevsec_secureapp.api

import com.google.gson.annotations.SerializedName

class AccountsByID (
    @SerializedName("account_name")
    val accountName: String,
    @SerializedName("amount")
    val accountAmount: Float,
    @SerializedName("currency")
    val accountCurrency: String,
    @SerializedName("iban")
    val accountIban: String
)