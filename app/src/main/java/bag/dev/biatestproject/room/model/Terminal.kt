package bag.dev.biatestproject.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terminal_table")
data class Terminal(
    @PrimaryKey
    var id: Int,                    //Terminal's Id
    var name: String,               //Name
    var latitude: Double,           //Latitude
    var longitude: Double,          //Longitude
    var receiveCargo: Boolean,      //Receive Cargo
    var giveoutCargo: Boolean,      //Giveout Cargo
    var defaultState: Boolean,      //Default
    var mapUrl: String,             //Maps.map.width.640.height.640.url
    var worktables: String,         //Worktables[].toString
    var address: String,            //address
    var distanceValue: Double       //distance
) {
    constructor() : this(0, "", 0.0, 0.0, false, false, false, "", "", "",0.0)
}