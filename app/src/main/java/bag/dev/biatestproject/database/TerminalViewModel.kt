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
    val readAllFromData:LiveData<List<Terminal>>
    val readAllToData:LiveData<List<Terminal>>
    private val repository: TerminalRepository

    init {
        val terminalDao = TerminalDatabase.getDatabase(application).terminalDao()
        repository = TerminalRepository(terminalDao)
        readAllData = repository.readAllData
        sortedDataByName = repository.sortedDataByName
//        sortedDataByAge = repository.sortedDataByAge
        readAllFromData = repository.readAllFromData
        readAllToData = repository.readAllToData
    }

//    fun addUser(user:User){
//        viewModelScope.launch (Dispatchers.IO){
//            repository.addUser(user)
//        }
//    }

    fun searchFromDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return repository.searchFromDatabase(searchQuery)
    }

    fun searchToDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return repository.searchToDatabase(searchQuery)
    }

    fun saveTransaction(transactions: Transactions){
        viewModelScope.launch (Dispatchers.IO){
            repository.saveTransaction(transactions)
        }
    }
}