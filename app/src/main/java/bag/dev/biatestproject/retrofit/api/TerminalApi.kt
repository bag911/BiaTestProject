package bag.dev.biatestproject.retrofit.api

import bag.dev.biatestproject.retrofit.model.Response
import retrofit2.Call
import retrofit2.http.GET

interface TerminalApi {
    @GET("/static/catalog/terminals_v3.json")
    fun getRoute(): Call<Response>
}