//1
package com.ecomarket.app.data
//2
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
//3
data class LoginBody(val email: String, val password: String)
//4
data class LoginResp(val token: String)
//5
data class MeResp(val name: String, val email: String)
//6
interface ApiService {
    //7
    @POST("/auth/login")
    suspend fun login(@Body body: LoginBody): LoginResp
    //8
    @GET("/auth/me")
    suspend fun me(@Header("Authorization") bearer: String): MeResp
}
//9
