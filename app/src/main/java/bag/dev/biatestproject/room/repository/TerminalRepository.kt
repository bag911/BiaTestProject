package bag.dev.biatestproject.room.repository

import androidx.lifecycle.LiveData
import bag.dev.biatestproject.room.model.Terminal
import bag.dev.biatestproject.room.database.TerminalDao
import bag.dev.biatestproject.room.model.Transactions


class TerminalRepository(private val terminalDao: TerminalDao) {
    val sortedFromDataByName: LiveData<List<Terminal>> = terminalDao.sortFromDataByName()
    val sortedToDataByName: LiveData<List<Terminal>> = terminalDao.sortToDataByName()
    val sortFromDataByDistance: LiveData<List<Terminal>> = terminalDao.sortFromDataByDistance()
    val sortToDataByDistance: LiveData<List<Terminal>> = terminalDao.sortToDataByDistance()
    val readAllFromData: LiveData<List<Terminal>> = terminalDao.readAllFromData()
    val readAllToData: LiveData<List<Terminal>> = terminalDao.readAllToData()


    fun getTerminalById(terminalId:Int):LiveData<Terminal>{
       return terminalDao.getTerminalById(terminalId)
    }

    fun searchFromDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return terminalDao.searchFromDatabase(searchQuery)
    }
    fun searchToDatabase(searchQuery:String):LiveData<List<Terminal>>{
        return terminalDao.searchToDatabase(searchQuery)
    }

    suspend fun saveTransaction(transactions: Transactions){
        terminalDao.saveTransaction(transactions)
    }

    suspend fun insert(vararg terminal: Terminal){
        terminalDao.insert(*terminal)
    }

    suspend fun delete(){
        terminalDao.delete()
    }
}