package bag.dev.biatestproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import bag.dev.biatestproject.adapters.ToListAdapter
import bag.dev.biatestproject.databinding.FragmentFromBinding

class ToFragment : Fragment() {
    private var _toBinding:FragmentFromBinding ? = null
    private val toBinding get() = _toBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toBinding = FragmentFromBinding.inflate(inflater,container,false)
        return toBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toBinding.fromRcView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        toBinding.fromRcView.adapter = ToListAdapter(arrayOf("Racoon","Spoon","Chan"))
    }

}