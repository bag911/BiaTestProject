package bag.dev.biatestproject.viewmodel

import androidx.lifecycle.ViewModel

class NavViewModel: ViewModel() {
    var fromId:Int = 0
    var toId:Int = 0

    fun Int.isSelected() = this>0

    fun checkFromItemSelected() = fromId.isSelected()
    fun checkToItemSelected() = toId.isSelected()
}