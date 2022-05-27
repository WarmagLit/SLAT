package com.tsu.slat.utils

import android.util.Base64
import com.tsu.slat.presentation.screens.foodsearch.OAuthQuery
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

object RequestBuilder {

    /**
     * app segnature method that is wanted from the api
     */
    private val APP_SIGNATURE_METHOD = "HmacSHA1"

    /**
     * http method that is used
     */
    private val HTTP_METHOD = "GET"

    /**
     * base url that is fetched
     */
    private val APP_URL = "https://platform.fatsecret.com/rest/server.api"

    /**
     * buildAuthParams builds a Array of Strings that has current authorities
     * demanded by fatSecret API
     *
     * @return array of the authorities
     */
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
        oauth.oauth_signature = sign(paramsToArray(oauth, id))

        //return this.APP_URL + "?" + paramsToString(params.toTypedArray())
        return oauth
    }

    fun paramsToArray(oauth: OAuthQuery, food_id: String): Array<String> {
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

    /**
     * encode encodes the given string and replaces unwanted characters
     *
     * @param encodable the string that is encoded
     * @return the string that is encoded
     */
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

    /**
     * paramsToString gets the array and joins it to a string with & separator
     *
     * @param params the array of params for the fetch url
     * @return the string with & separator
     */
    fun paramsToString(params: Array<String>) : String {
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
    fun sign(params: Array<String>) : String {
        val encodedURI: String = encode(this.APP_URL)
        val encodedParams = encode(paramsToString(params))

        val p = arrayOf(this.HTTP_METHOD, encodedURI, encodedParams)
        val text = p.joinToString(separator = "&")
        val key = "346f6ebeb8c14366bd664b9ca23db133" + "&"
        val secretKey = SecretKeySpec(key.toByteArray(), this.APP_SIGNATURE_METHOD)
        var sign = ""
        try {
            val m: Mac = Mac.getInstance(this.APP_SIGNATURE_METHOD)
            m.init(secretKey)
            sign = encode(String(Base64.encode(m.doFinal(text.toByteArray()), Base64.DEFAULT)).trim())
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
    fun nonce(): String {
        /*
        val r = Random()
        val n = StringBuffer()
        for (i in 0 until r.nextInt(8) + 2) {
            n.append(r.nextInt(26).toString() + 'a')
        }
        return n.toString()*/
        return "99991"
    }
}