package bag.dev.biatestproject.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
data class Transactions (
    @PrimaryKey(autoGenerate = true)
    var id:Int,             //Transaction's id
    var fromId:Int,         //FromFragment's item id
    var toId:Int            //ToFragment's item id
)