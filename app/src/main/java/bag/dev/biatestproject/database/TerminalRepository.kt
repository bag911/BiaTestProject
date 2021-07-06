package bag.dev.biatestproject.database

import androidx.lifecycle.LiveData


class TerminalRepository(private val terminalDao: TerminalDao) {
    val readAllData: LiveData<List<Terminal>> = terminalDao.readAllData()
    val sortedDataByName: LiveData<List<Terminal>> = terminalDao.sortDataByName()

    val readAllFromData: LiveData<List<Terminal>> = terminalDao.readAllFromData()
    val readAllToData: LiveData<List<Terminal>> = terminalDao.readAllToData()

//    val sortedDataByAge: LiveData<List<Terminal>> = terminalDao.sortDataByAge()

//    suspend fun addTransaction(user:Terminal){
//        terminalDao.addTerminal(user)
//    }

    fun searchFromDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return terminalDao.searchFromDatabase(searchQuery)
    }
    fun searchToDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return terminalDao.searchToDatabase(searchQuery)
    }

    suspend fun saveTransaction(transactions: Transactions){
        terminalDao.saveTransaction(transactions)
    }
}