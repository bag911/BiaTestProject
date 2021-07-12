package bag.dev.biatestproject.retrofit.model

import com.google.gson.annotations.SerializedName


data class Response(

	@field:SerializedName("city")
	val city: List<CityItem?>? = null
)

data class JsonMember640(

	@field:SerializedName("height")
	val height: Height? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class AddressCode(

	@field:SerializedName("street_code")
	val streetCode: String? = null
)

data class Height(

	@field:SerializedName("352")
	val jsonMember352: JsonMember352? = null,

	@field:SerializedName("960")
	val jsonMember960: JsonMember960? = null,

	@field:SerializedName("640")
	val jsonMember640: JsonMember640? = null
)

data class JsonMember352(

	@field:SerializedName("url")
	val url: String? = null
)

data class Worktables(

	@field:SerializedName("worktable")
	val worktable: List<WorktableItem?>? = null
)

data class Terminals(

	@field:SerializedName("terminal")
	val terminal: List<TerminalItem?>? = null
)

data class WorktableItem(

	@field:SerializedName("sunday")
	val sunday: String? = null,

	@field:SerializedName("saturday")
	val saturday: String? = null,

	@field:SerializedName("tuesday")
	val tuesday: String? = null,

	@field:SerializedName("wednesday")
	val wednesday: String? = null,

	@field:SerializedName("thursday")
	val thursday: String? = null,

	@field:SerializedName("friday")
	val friday: String? = null,

	@field:SerializedName("department")
	val department: String? = null,

	@field:SerializedName("monday")
	val monday: String? = null,

	@field:SerializedName("timetable")
	val timetable: String? = null
)

data class JsonMember944(

	@field:SerializedName("height")
	val height: Height? = null
)

data class CityItem(

	@field:SerializedName("day2daySFRequest")
	val day2daySFRequest: String? = null,

	@field:SerializedName("freeStorageDays")
	val freeStorageDays: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("requestEndTime")
	val requestEndTime: String? = null,

	@field:SerializedName("cityID")
	val cityID: Int? = null,

	@field:SerializedName("preorderRequest")
	val preorderRequest: String? = null,

	@field:SerializedName("terminals")
	val terminals: Terminals? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("sfrequestEndTime")
	val sfrequestEndTime: String? = null,

	@field:SerializedName("timeshift")
	val timeshift: String? = null,

	@field:SerializedName("day2dayRequest")
	val day2dayRequest: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null
)

data class JsonMember960(

	@field:SerializedName("height")
	val height: Height? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class PhonesItem(

	@field:SerializedName("number")
	val number: String? = null,

	@field:SerializedName("comment")
	val comment: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("primary")
	val primary: Boolean? = null
)

data class Maps(

	@field:SerializedName("width")
	val width: Width? = null
)

data class Width(

	@field:SerializedName("640")
	val jsonMember640: JsonMember640? = null,

	@field:SerializedName("960")
	val jsonMember960: JsonMember960? = null,

	@field:SerializedName("944")
	val jsonMember944: JsonMember944? = null
)

data class CalcSchedule(

	@field:SerializedName("arrival")
	val arrival: String? = null,

	@field:SerializedName("derival")
	val derival: String? = null
)

data class TerminalItem(

	@field:SerializedName("calcSchedule")
	val calcSchedule: CalcSchedule? = null,

	@field:SerializedName("mail")
	val mail: String? = null,

	@field:SerializedName("maxShippingWeight")
	val maxShippingWeight: Double? = null,

	@field:SerializedName("latitude")
	val latitude: String? = null,

	@field:SerializedName("phones")
	val phones: List<Any?>? = null,

	@field:SerializedName("maxShippingVolume")
	val maxShippingVolume: Double? = null,

	@field:SerializedName("storage")
	val storage: Boolean? = null,

	@field:SerializedName("default")
	val jsonMemberDefault: Boolean? = null,

	@field:SerializedName("giveoutCargo")
	val giveoutCargo: Boolean? = null,

	@field:SerializedName("maxHeight")
	val maxHeight: Double? = null,

	@field:SerializedName("isPVZ")
	val isPVZ: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("longitude")
	val longitude: String? = null,

	@field:SerializedName("maxWidth")
	val maxWidth: Double? = null,

	@field:SerializedName("addressCode")
	val addressCode: Any? = null,

	@field:SerializedName("isOffice")
	val isOffice: Boolean? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("maps")
	val maps: Maps? = null,

	@field:SerializedName("maxWeight")
	val maxWeight: Double? = null,

	@field:SerializedName("maxVolume")
	val maxVolume: Double? = null,

	@field:SerializedName("worktables")
	val worktables: Worktables? = null,

	@field:SerializedName("receiveCargo")
	val receiveCargo: Boolean? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("fullAddress")
	val fullAddress: String? = null,

	@field:SerializedName("maxLength")
	val maxLength: Double? = null
)
