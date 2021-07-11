package bag.dev.biatestproject.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "terminal_table")
data class Terminal(
    @PrimaryKey
    var id: Int,
    var name: String,
    var latitude: Double,
    var longitude: Double,
    var receiveCargo: Boolean,
    var giveoutCargo: Boolean,
    var defaultState: Boolean,
    var mapUrl: String,
    var worktables: String,
    var address: String,
    var distanceValue: Double
) {
    constructor() : this(0, "", 0.0, 0.0, false, false, false, "", "", "",0.0)
}
