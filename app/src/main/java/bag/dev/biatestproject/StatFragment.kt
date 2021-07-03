package bag.dev.biatestproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import bag.dev.biatestproject.databinding.FragmentStatBinding

class StatFragment : Fragment() {

    private var _statBinding:FragmentStatBinding ? = null
    private val statBinding get() = _statBinding!!

    private val navViewModel:NavViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _statBinding = FragmentStatBinding.inflate(inflater,container,false)
        return statBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO Выгрузка данных при открытии фрагмента по id
//        if(navViewModel.title != "Empty"){
//        }

        statBinding.from.setOnClickListener {
            val bundle = bundleOf("pointer" to 0)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment,bundle)

        }
        statBinding.to.setOnClickListener {
            val bundle = bundleOf("pointer" to 1)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment,bundle)
        }
    }

}