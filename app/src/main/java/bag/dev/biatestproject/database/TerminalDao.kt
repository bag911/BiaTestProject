package bag.dev.biatestproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TerminalDao {
    //TODO: Сделать сохранение транзакции в бд
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addUser(terminal:Terminal)

    @Query("SELECT * FROM terminal_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Terminal>>

    @Query("SELECT * FROM terminal_table ORDER BY name ASC")
    fun sortDataByName(): LiveData<List<Terminal>>


    //TODO: Сделать sortByDistance

//    @Query("SELECT * FROM terminal_table ORDER BY age ASC")
//    fun sortDataByAge(): LiveData<List<Terminal>>
}