package com.app.gameshelf.data.repository

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.app.gameshelf.data.api.RetrofitClient
import com.app.gameshelf.data.model.LinkSteamRequest
import com.app.gameshelf.data.model.LinkSteamResponse
import com.app.gameshelf.data.model.LoginRequest
import com.app.gameshelf.data.model.LoginResponse
import com.app.gameshelf.data.model.SignupRequest
import com.app.gameshelf.data.model.SignupResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class AuthRepository(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "auth_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    suspend fun login(request: LoginRequest): Response<LoginResponse> {
        val response = RetrofitClient.apiService.login(request)
        if (response.isSuccessful) {
            response.body()?.let {
                saveLoginResponse(it)
            }
        }
        return response
    }

    suspend fun signup(request: SignupRequest): Response<SignupResponse> {
        return RetrofitClient.apiService.signup(request)
    }

    suspend fun linkSteam(steamId: String): Response<LinkSteamResponse> {
        val token = getToken() ?: return Response.error(401, "Unauthorized".toResponseBody("text/plain".toMediaTypeOrNull()))
        val response = RetrofitClient.apiService.linkSteam(
            token = "Bearer $token",
            request = LinkSteamRequest(steamId)
        )
        if (response.isSuccessful) {
            response.body()?.user?.let { user ->
                saveUser(user)
            }
        }
        return response
    }

    private fun saveLoginResponse(loginResponse: LoginResponse) {
        sharedPreferences.edit {
            putString("jwt_token", loginResponse.token)
            putString("uid", loginResponse.uid)
            putString("steam_id", loginResponse.steamId)

            val gson = Gson()
            val gamesJson = gson.toJson(loginResponse.ownedGames)
            putString("owned_games", gamesJson)
        }
    }

    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    fun clearToken() {
        sharedPreferences.edit {
            remove("jwt_token")
            remove("uid")
            remove("steam_id")
            remove("owned_games")
        }
    }

    private fun saveUser(user: com.app.gameshelf.data.model.User) {
        sharedPreferences.edit {
            putString("uid", user.uid)
            putString("steam_id", user.steamId)

            // Extract appids and store them to be consistent with LoginResponse
            val appIds = user.ownedGames?.map { it.appid }
            val gson = Gson()
            val gamesJson = gson.toJson(appIds)
            putString("owned_games", gamesJson)
        }
    }

    fun getOwnedGames(): List<Int> {
        val gamesJson = sharedPreferences.getString("owned_games", null)
        return if (gamesJson != null) {
            val gson = Gson()
            val type = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(gamesJson, type) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun getSteamId(): String? {
        return sharedPreferences.getString("steam_id", null)
    }

    fun getUid(): String? {
        return sharedPreferences.getString("uid", null)
    }
}