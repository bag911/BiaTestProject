package bag.dev.biatestproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terminal_table")
data class Terminal (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name:String,
    val latitude:Double,
    val longitude:Double,
    val receiveCargo: Boolean,
    val giveoutCargo: Boolean,
    val default: Boolean
    //TODO: Добавить ворктейбл в бд
//    val worktable: List<>,
    )
