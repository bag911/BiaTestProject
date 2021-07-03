package bag.dev.biatestproject.database

import androidx.lifecycle.LiveData


class TerminalRepository(private val terminalDao: TerminalDao) {
    val readAllData: LiveData<List<Terminal>> = terminalDao.readAllData()
    val sortedDataByName: LiveData<List<Terminal>> = terminalDao.sortDataByName()
//    val sortedDataByAge: LiveData<List<Terminal>> = terminalDao.sortDataByAge()

//    suspend fun addTransaction(user:Terminal){
//        terminalDao.addTerminal(user)
//    }
}