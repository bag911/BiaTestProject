package bag.dev.biatestproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transactions (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var fromId:Int,
    var toId:Int
)