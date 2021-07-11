package bag.dev.biatestproject.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface TerminalApi {
    @GET("/static/catalog/terminals_v3.json")
    fun getRoute(): Call<Response>
}