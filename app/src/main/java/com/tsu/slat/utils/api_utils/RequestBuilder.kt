package com.tsu.slat.utils.api_utils

import android.util.Base64
import android.util.Log
import com.tsu.slat.presentation.entity.OAuthQuery
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object RequestBuilder {

    private val APP_SIGNATURE_METHOD = "HmacSHA1"

    private val HTTP_METHOD = "GET"

    private val APP_URL = "https://platform.fatsecret.com/rest/server.api"

    private fun buildAuthParams(oAuthQuery: OAuthQuery)  {
        oAuthQuery.oauth_consumer_key = "f0ab4d1c295f45e1bdd8376cb3a99f72"
        oAuthQuery.oauth_signature_method = "HMAC-SHA1"
        //oAuthQuery.oauth_timestamp = (System.currentTimeMillis() / 1000).toString()
        oAuthQuery.oauth_timestamp = "1652467421"
        oAuthQuery.oauth_nonce = nonce()
        oAuthQuery.format = "json"
    }

    /**
     * getIdByBarcode creates the url for fetching the id of the product for barcode given
     *
     * @param barcode the barcode that id is based on
     * @return the url with current authorities
     */
    /*
    fun getIdByBarcode(barcode : String) : String {
        val params: MutableMap<String, String> = buildAuthParams()
        params.put("method", "food.find_id_for_barcode")
        params.put("barcode", "barcode")
        params.put("oauth_token", "f0ab4d1c295f45e1bdd8376cb3a99f72")
        params.put("oauth_signature", sign(paramsToArray(params)))

        return this.APP_URL + "?" + paramsToString(params.toTypedArray())
    }*/
    /**
     * getFoodById creates the url for fetching the food of the product for id given
     *
     * @param id the id that food is based on
     * @return the url with current authorities
     */
    fun getFoodById(id : String) : OAuthQuery {
        val oauth = OAuthQuery()
        buildAuthParams(oauth)

        oauth.method=  "food.get.v2"
        oauth.oauth_signature = sign(paramsIdToArray(oauth, id))

        Log.d("Result", oauth.oauth_signature!!)

        //return this.APP_URL + "?" + paramsToString(params.toTypedArray())
        return oauth
    }

    fun findFood(food : String) : OAuthQuery {
        val oauth = OAuthQuery()
        buildAuthParams(oauth)

        oauth.method=  "foods.search"
        oauth.oauth_signature = sign(paramsSearchToArray(oauth, food))

        return oauth
    }

    fun getFoodByBarcode(barcode: String): OAuthQuery {
        val oauth = OAuthQuery()
        buildAuthParams(oauth)

        oauth.method=  "food.find_id_for_barcode"
        oauth.oauth_signature = sign(paramsBarcodeToArray(oauth, barcode))

        return oauth
    }

    private fun paramsIdToArray(oauth: OAuthQuery, food_id: String): Array<String> {
        var res = emptyArray<String>()
        res += "oauth_consumer_key=" + oauth.oauth_consumer_key
        res += "oauth_signature_method=" + oauth.oauth_signature_method
        res += "oauth_timestamp=" +oauth.oauth_timestamp
        res += "oauth_nonce=" +oauth.oauth_nonce
        res += "oauth_version=" + oauth.version
        res += "format=" +oauth.format

        res += "method=" + oauth.method
        res += "food_id=" + food_id

        return res
    }

    private fun paramsSearchToArray(oauth: OAuthQuery, food: String): Array<String> {
        var res = emptyArray<String>()
        res += "oauth_consumer_key=" + oauth.oauth_consumer_key
        res += "oauth_signature_method=" + oauth.oauth_signature_method
        res += "oauth_timestamp=" +oauth.oauth_timestamp
        res += "oauth_nonce=" +oauth.oauth_nonce
        res += "oauth_version=" + oauth.version
        res += "format=" +oauth.format

        res += "method=" + oauth.method
        res += "search_expression=" + food

        res+= "region=RU"
        res+= "language=ru"
        return res
    }

    private fun paramsBarcodeToArray(oauth: OAuthQuery, barcode: String): Array<String> {
        var res = emptyArray<String>()
        res += "oauth_consumer_key=" + oauth.oauth_consumer_key
        res += "oauth_signature_method=" + oauth.oauth_signature_method
        res += "oauth_timestamp=" +oauth.oauth_timestamp
        res += "oauth_nonce=" +oauth.oauth_nonce
        res += "oauth_version=" + oauth.version
        res += "format=" +oauth.format

        res += "method=" + oauth.method
        res += "barcode=" + barcode
        return res
    }


    private fun encode(encodable: String) : String {
        return try {
            URLEncoder.encode(encodable, "utf-8")
                .replace("+", "%20")
                .replace("!", "%21")
                .replace("*", "%2A")
                .replace("\\", "%27")
                .replace("(", "%28")
                .replace(")", "%29")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e.message, e)
        }
    }


    private fun paramsToString(params: Array<String>) : String {
        val p = params
        Arrays.sort(p)
        return p.joinToString(separator = "&")
    }

    /**
     * sign creates a a unique signature for the fetch method that can be
     * used only once.
     *
     * @param params array of params that is used for the sign
     * @return a string with the unique signature
     */
    private fun sign(params: Array<String>) : String {
        val encodedURI: String = encode(APP_URL)
        Log.d("BeforeEncode", paramsToString(params))
        val encodedParams = encode(paramsToString(params))
        Log.d("After", encodedParams)

        val p = arrayOf(HTTP_METHOD, encodedURI, encodedParams)
        val text = p.joinToString(separator = "&")
        val key = "346f6ebeb8c14366bd664b9ca23db133" + "&"
        val secretKey = SecretKeySpec(key.toByteArray(), APP_SIGNATURE_METHOD)
        var sign = ""
        try {
            val m: Mac = Mac.getInstance(APP_SIGNATURE_METHOD)
            m.init(secretKey)

            sign = String(Base64.encode(m.doFinal(text.toByteArray()), Base64.DEFAULT)).trim()
        } catch (e: NoSuchAlgorithmException) {
            println("NoSuchAlgorithmException: " + e.message)
        } catch (e: InvalidKeyException) {
            println("InvalidKeyException: " + e.message)
        }
        return sign
    }


    /**
     * nonce creates randomly generated nonce value for the fetching.
     *
     * @return the randomly generated value for nonce.
     */
    private fun nonce(): String {
        val r = Random()
        return (r.nextDouble() * 100000).toInt().toString()

    }
}