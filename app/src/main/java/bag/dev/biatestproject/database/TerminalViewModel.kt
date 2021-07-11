package bag.dev.biatestproject.database

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bag.dev.biatestproject.BASE_URL
import bag.dev.biatestproject.retrofit.TerminalApi
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class TerminalViewModel(application: Application) : AndroidViewModel(application) {

    //    val readAllData: LiveData<List<Terminal>>
    val sortFromDataByName: LiveData<List<Terminal>>
    val sortToDataByName: LiveData<List<Terminal>>

    //    val sortedDataByAge: LiveData<List<Terminal>>
    val readAllFromData: LiveData<List<Terminal>>
    val readAllToData: LiveData<List<Terminal>>
    val sortFromDataByDistance: LiveData<List<Terminal>>
    val sortToDataByDistance: LiveData<List<Terminal>>
    private val repository: TerminalRepository

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TerminalApi::class.java)

    init {
        val terminalDao = TerminalDatabase.getDatabase(application).terminalDao()
        repository = TerminalRepository(terminalDao)
        sortFromDataByName = repository.sortedFromDataByName
        sortToDataByName = repository.sortedToDataByName
        readAllFromData = repository.readAllFromData
        readAllToData = repository.readAllToData
        sortFromDataByDistance = repository.sortFromDataByDistance
        sortToDataByDistance = repository.sortToDataByDistance
    }


    fun getTerminalById(terminalId: Int): LiveData<Terminal> {
        return repository.getTerminalById(terminalId)
    }

    fun searchFromDatabase(searchQuery: String): LiveData<List<Terminal>> {
        return repository.searchFromDatabase(searchQuery)
    }

    fun searchToDatabase(searchQuery: String): LiveData<List<Terminal>> {
        return repository.searchToDatabase(searchQuery)
    }

    fun saveTransaction(transactions: Transactions) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveTransaction(transactions)
        }
    }


    fun getData(context: Context, myPos: LatLng) {

        viewModelScope.launch(Dispatchers.IO) {
            getInfo(context, myPos)
        }

//        go(context)

    }

    suspend fun getInfo(context: Context, myPos: LatLng) {
        try {
            var strWorktables = ""
            val dataList = arrayListOf<Terminal>()
            val response = api.getRoute().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("DOTA", data.city?.size.toString())
                for (i in data.city!!) {
                    for (j in i?.terminals?.terminal!!) {
                        if (j != null) {

                            for (w in j.worktables?.worktable!!) {
                                strWorktables += "${w?.department}|${w?.monday}|${w?.tuesday}|${w?.wednesday}|${w?.thursday}|${w?.friday}|${w?.saturday}|${w?.sunday}|${w?.timetable}\n"
                            }
                            dataList.add(
                                Terminal(
                                    j.id!!.toInt(),
                                    j.name!!,
                                    j.latitude!!.toDouble(),
                                    j.longitude!!.toDouble(),
                                    j.receiveCargo!!,
                                    j.giveoutCargo!!,
                                    j.jsonMemberDefault!!,
                                    j.maps?.width?.jsonMember640?.height?.jsonMember640?.url.toString(),
                                    strWorktables,
                                    j.address.toString(),
                                    0.0
                                )
                            )
                            strWorktables = ""
                        }
                    }
                }
                insert(dataList)
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Something went wrong...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insert(dataList: ArrayList<Terminal>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(*dataList.toTypedArray())
        }
    }


}