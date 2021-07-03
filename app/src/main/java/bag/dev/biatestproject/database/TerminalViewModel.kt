package bag.dev.biatestproject.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TerminalViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Terminal>>
    val sortedDataByName: LiveData<List<Terminal>>
//    val sortedDataByAge: LiveData<List<Terminal>>
    private val repository: TerminalRepository

    init {
        val terminalDao = TerminalDatabase.getDatabase(application).terminalDao()
        repository = TerminalRepository(terminalDao)
        readAllData = repository.readAllData
        sortedDataByName = repository.sortedDataByName
//        sortedDataByAge = repository.sortedDataByAge
    }

//    fun addUser(user:User){
//        viewModelScope.launch (Dispatchers.IO){
//            repository.addUser(user)
//        }
//    }
}