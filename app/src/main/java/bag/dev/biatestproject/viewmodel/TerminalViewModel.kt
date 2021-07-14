package bag.dev.biatestproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import bag.dev.biatestproject.retrofit.api.TerminalApi
import bag.dev.biatestproject.room.database.TerminalDatabase
import bag.dev.biatestproject.room.model.Terminal
import bag.dev.biatestproject.room.model.Transactions
import bag.dev.biatestproject.room.repository.TerminalRepository
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class TerminalViewModel(application: Application) : AndroidViewModel(application) {

    val sortFromDataByName: LiveData<List<Terminal>>
    val sortToDataByName: LiveData<List<Terminal>>
    val readAllFromData: LiveData<List<Terminal>>
    val readAllToData: LiveData<List<Terminal>>
    val sortFromDataByDistance: LiveData<List<Terminal>>
    val sortToDataByDistance: LiveData<List<Terminal>>
    private val repository: TerminalRepository

    private val api = Retrofit.Builder()
        .baseUrl("https://api.dellin.ru")
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

    private fun insert(dataList: ArrayList<Terminal>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(*dataList.toTypedArray())
        }
    }
//    private fun delete() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.delete()
//        }
//    }


    //Parsing from json
    fun getData(myPos: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            getInfo(myPos)
        }
    }

    private suspend fun getInfo(myPos: LatLng) {
        try {
            var strWorktables = ""
            val dataList = arrayListOf<Terminal>()
            val response = api.getRoute().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                for (i in data.city!!) {
                    for (j in i?.terminals?.terminal!!) {
                        if (j != null) {
                            for (w in j.worktables?.worktable!!) {
                                strWorktables += "${w?.department}:\nПн: ${w?.monday}\nВт: ${w?.tuesday}\nСр: ${w?.wednesday}\nЧт: ${w?.thursday}\nПт: ${w?.friday}\nСб: ${w?.saturday}\nВс: ${w?.sunday}\n${w?.timetable}\n\n"
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
                                    SphericalUtil.computeDistanceBetween(myPos, LatLng(j.latitude.toDouble(),j.longitude.toDouble()))
                                )
                            )
                            strWorktables = ""
                        }
                    }
                }
                insert(dataList)
            }
        } catch (e: Exception) {
            //Todo check exception
        }
    }
}