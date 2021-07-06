package bag.dev.biatestproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TerminalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTransaction(transactions: Transactions)

    @Query("SELECT * FROM terminal_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table ORDER BY name ASC")
    fun sortDataByName(): LiveData<List<Terminal>>


    //TODO: Сделать sortByDistance

//    @Query("SELECT * FROM terminal_table ORDER BY age ASC")
//    fun sortDataByAge(): LiveData<List<Terminal>>


    @Query("SELECT * FROM  terminal_table WHERE receiveCargo = 1")
    fun readAllFromData():LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1")
    fun readAllToData():LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE receiveCargo = 1 AND name LIKE :searchQuery")
    fun searchFromDatabase(searchQuery:String): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1 AND name LIKE :searchQuery")
    fun searchToDatabase(searchQuery:String): LiveData<List<Terminal>>


}