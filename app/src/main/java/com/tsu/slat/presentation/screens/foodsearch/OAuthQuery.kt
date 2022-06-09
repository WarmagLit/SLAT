package com.tsu.slat.presentation.screens.foodsearch

data class OAuthQuery(
    var method: String? = null,
    var oauth_signature: String? = null,
    var oauth_consumer_key: String? = null,
    var oauth_signature_method: String? = null,
    var oauth_timestamp: String? = null,
    var oauth_nonce: String? = null,
    var format: String? = null,
    var version: String = "1.0"
)
