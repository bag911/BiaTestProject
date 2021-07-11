package bag.dev.biatestproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import bag.dev.biatestproject.NavViewModel
import bag.dev.biatestproject.R
import bag.dev.biatestproject.database.TerminalViewModel
import bag.dev.biatestproject.database.Transactions
import bag.dev.biatestproject.databinding.FragmentStatBinding
import com.bumptech.glide.Glide


class StatFragment : Fragment() {

    private var _statBinding: FragmentStatBinding? = null
    private val statBinding get() = _statBinding!!

    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel: TerminalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _statBinding = FragmentStatBinding.inflate(inflater, container, false)

        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        Log.d("DOTA", "${navViewModel.fromId}   ${navViewModel.toId}")

        if (navViewModel.fromId > 0) {
            terminalViewModel.getTerminalById(navViewModel.fromId)
                .observe(viewLifecycleOwner, { terminal ->
                    statBinding.nameFrom.text = terminal.name
                    statBinding.addressFrom.text = terminal.address
                    statBinding.distanceFrom.text = terminal.distanceValue.toString()
                    statBinding.workTableFrom.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl).circleCrop()
                        .placeholder(R.drawable.sample).circleCrop().into(statBinding.imageViewFrom)
                })
        }
        if (navViewModel.toId > 0) {
            terminalViewModel.getTerminalById(navViewModel.toId)
                .observe(viewLifecycleOwner, { terminal ->
                    statBinding.nameTo.text = terminal.name
                    statBinding.addressTo.text = terminal.address
                    statBinding.distanceTo.text = terminal.distanceValue.toString()
                    statBinding.workTableTo.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl).circleCrop()
                        .placeholder(R.drawable.sample).into(statBinding.imageViewTo)
                })
        }

        return statBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO Выгрузка данных при открытии фрагмента по id

        statBinding.saveBtn.isEnabled = navViewModel.fromId > 0 && navViewModel.toId > 0

        statBinding.from.setOnClickListener {
            val bundle = bundleOf("pointer" to 0)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment, bundle)

        }
        statBinding.to.setOnClickListener {
            val bundle = bundleOf("pointer" to 1)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment, bundle)
        }
        statBinding.saveBtn.setOnClickListener {
            terminalViewModel.saveTransaction(
                Transactions(
                    0,
                    navViewModel.fromId,
                    navViewModel.toId
                )
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.sortByName).isVisible = false
        menu.findItem(R.id.sortByDistance).isVisible = false
        menu.findItem(R.id.search).isVisible = false

    }


}