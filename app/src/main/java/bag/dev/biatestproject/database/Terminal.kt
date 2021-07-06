package bag.dev.biatestproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terminal_table")
data class Terminal (
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var latitude:Double,
    var longitude:Double,
    var receiveCargo: Boolean,
    var giveoutCargo: Boolean,
    var defaultState: Boolean
    //TODO: Добавить ворктейбл в бд
//    var worktable: List<>,
    ){
    constructor() : this(0, "", 0.0, 0.0, false, false, false)
}
