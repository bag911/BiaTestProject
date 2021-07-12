package bag.dev.biatestproject.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bag.dev.biatestproject.room.model.Terminal
import bag.dev.biatestproject.room.model.Transactions


@Dao
interface TerminalDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveTransaction(transactions: Transactions)

    @Query("SELECT * FROM terminal_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE receiveCargo = 1 ORDER BY name ASC")
    fun sortFromDataByName(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1 ORDER BY name ASC")
    fun sortToDataByName(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE id = :terminalId")
    fun getTerminalById(terminalId:Int):LiveData<Terminal>

    @Query("SELECT * FROM terminal_table WHERE receiveCargo = 1 ORDER BY distanceValue ASC")
    fun sortFromDataByDistance(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1 ORDER BY distanceValue ASC")
    fun sortToDataByDistance(): LiveData<List<Terminal>>


    @Query("SELECT * FROM  terminal_table WHERE receiveCargo = 1")
    fun readAllFromData():LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1")
    fun readAllToData():LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE receiveCargo = 1 AND name LIKE :searchQuery")
    fun searchFromDatabase(searchQuery:String): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table WHERE defaultState = 1 AND giveoutCargo = 1 AND name LIKE :searchQuery")
    fun searchToDatabase(searchQuery:String): LiveData<List<Terminal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg terminal: Terminal)

    @Query("DELETE FROM terminal_table")
    suspend fun delete()
}