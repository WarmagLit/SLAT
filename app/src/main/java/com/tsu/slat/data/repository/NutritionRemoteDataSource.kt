package com.tsu.slat.data.repository

import android.util.Log
import com.fatsecret.platform.model.CompactFood
import com.fatsecret.platform.services.FatsecretService
import com.tsu.slat.data.api.NutritionApi
import com.tsu.slat.presentation.screens.foodsearch.OAuthQuery
import retrofit2.Response
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

interface NutritionRemoteDataSource {
    suspend fun getFoodById(food_id: String, params: OAuthQuery):  Response<CompactFood>
}

class NutritionRemoteDataSourceImpl(private val nutritionApi: NutritionApi) : NutritionRemoteDataSource {

    override suspend fun getFoodById(food_id: String, params: OAuthQuery): Response<CompactFood> {
        /*
        val timestamp = "1652227199"
        Log.d("Tag", "Timestamp" + timestamp)
        val method = "food.get.v2"
        val consumer_key = "f0ab4d1c295f45e1bdd8376cb3a99f72"
        val consumer_secret = "346f6ebeb8c14366bd664b9ca23db133"
        val nonce = "1"
        val signature_method = "HMAC-SHA1"
        val oauth_version = "1.0"
        //val secret = "346f6ebeb8c14366bd664b9ca23db133"
       // var result = FatSecretResult()
        //val api = FatSecretAPI(consumer_key, consumer_secret)
        //val signature = api.ProfileGetAuth(consumer_key)
        val service = FatsecretService(consumer_key, consumer_secret)
        //val res = service.searchFoods("cookie")
        //val vrr = Request(consumer_key, consumer_secret)
        //val res = vrr.searchFoods("cookie", 0)
        //Log.d("tag", res.toString())
        val signature = generateSignature(method, consumer_key, nonce, signature_method, timestamp, oauth_version)
        val search = searchfood("cookie", service)
        */
        Log.d("Tag", "Signature: " +  params.oauth_signature!!)
        return nutritionApi.getNutrition(
            food_id,
            params.format!!,
            params.method!!,
            params.oauth_consumer_key!!,
            params.oauth_nonce!!,
            params.oauth_signature!!,
            params.oauth_signature_method!!,
            params.oauth_timestamp!!,
            params.version!!
        )
        //nutritionApi.getNutrition(method, consumer_key, nonce, signature, signature_method, timestamp, oauth_version)
    }

    fun searchfood(searchItem: String, service: FatsecretService): com.fatsecret.platform.services.Response<CompactFood>? {
        val response = service.searchFoods(searchItem)
        return response
    }

    fun generateSignature(method: String,
                          oauth_consumer_key: String,
                          nonce: String,
                          signature_method: String,
                          timestamp: String,
                          oauth_version: String): String {
        val normalised = "GET&https%3A%2F%2Fplatform.fatsecret.com%2Frest%2Fserver.api&food_id%3D33691" +
                "%26method%3D" + method +
                "%26oauth_consumer_key%3D" + oauth_consumer_key +
                "%26oauth_nonce%3D" + nonce +
                "%26oauth_signature_method%3D" + signature_method +
                "%26oauth_timestamp%3D" + timestamp +
                "%26oauth_version%3D" + oauth_version
        Log.d("Tag", "Normalised:$normalised")
        val res = calculateRFC2104HMAC(normalised, "346f6ebeb8c14366bd664b9ca23db133&")
        Log.d("Tag", "Signature:$res")
        return  res
    }

    private val HMAC_SHA1_ALGORITHM = "HmacSHA1"

    private fun toHexString(bytes: ByteArray): String {
        val formatter = Formatter()
        for (b in bytes) {
            formatter.format("%02x", b)
        }
        return formatter.toString()
    }

    fun calculateRFC2104HMAC(data: String, key: String): String {
        val signingKey = SecretKeySpec(key.toByteArray(), HMAC_SHA1_ALGORITHM)
        val mac: Mac = Mac.getInstance(HMAC_SHA1_ALGORITHM)
        mac.init(signingKey)
        return toHexString(mac.doFinal(data.toByteArray()))
    }

}