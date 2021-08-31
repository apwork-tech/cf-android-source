package com.wallet.cryptofuelx.main.data.remote.response.wallet_transaction


import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("address")
    var address: String,
    @SerializedName("address_type")
    var addressType: String,
    @SerializedName("amount")
    var amount: String,
    @SerializedName("confirmations")
    var confirmations: Int,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("fees")
    var fees: Int,
    @SerializedName("id")
    var id: Int,
    @SerializedName("receiver_wallet")
    var receiverWallet: ReceiverWallet,
    @SerializedName("receiver_wallet_id")
    var receiverWalletId: Int,
    @SerializedName("receiver_wallet_name")
    var receiverWalletName: String,
    @SerializedName("sender_wallet")
    var senderWallet: SenderWallet,
    @SerializedName("sender_wallet_id")
    var senderWalletId: Int,
    @SerializedName("sender_wallet_name")
    var senderWalletName: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("status_text")
    var statusText: String,
    @SerializedName("transaction_id")
    var transactionId: String,
    @SerializedName("transaction_type")
    var transactionType: Int,
    @SerializedName("updated_at")
    var updatedAt: String
)