package com.wallet.cryptofuelx.main.data.local.model

/**
 * This is model class for news of our application.
 * @property title title of the item
 * @property resourceId resource id of the item
 * @author Mohd. Asfaq-E-Azam Rifat
 */
data class News(
        val id: Long,
        val title: String,
        val time: Long,
        val description: String?,
        val resourceId: Int?)