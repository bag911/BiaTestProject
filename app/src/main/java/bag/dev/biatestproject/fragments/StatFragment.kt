package bag.dev.biatestproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import bag.dev.biatestproject.viewmodel.NavViewModel
import bag.dev.biatestproject.R
import bag.dev.biatestproject.viewmodel.TerminalViewModel
import bag.dev.biatestproject.room.model.Transactions
import bag.dev.biatestproject.databinding.FragmentStatBinding
import com.bumptech.glide.Glide
import java.math.BigDecimal
import java.math.RoundingMode


class StatFragment : Fragment() {

    private var _statBinding: FragmentStatBinding? = null
    private val statBinding get() = _statBinding!!


    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel: TerminalViewModel

    private var appBarLogic:AppBarLogic ?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appBarLogic = context as AppBarLogic
    }

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

        //Checking selected From terminals
        if (navViewModel.checkFromItemSelected()) {
            terminalViewModel.getTerminalById(navViewModel.fromId)
                .observe(viewLifecycleOwner, { terminal ->
                    terminal.name.also { statBinding.nameFrom.text = it }
                    terminal.address.also { statBinding.addressFrom.text = it }
                    "${BigDecimal(terminal.distanceValue/1000.0).setScale(2,RoundingMode.HALF_EVEN)} км".also { statBinding.distanceFrom.text = it }
                    statBinding.workTableFrom.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl)
                        .placeholder(R.drawable.sample).into(statBinding.imageViewFrom)
                })
        }
        //Checking selected To terminals
        if (navViewModel.checkToItemSelected()) {
            terminalViewModel.getTerminalById(navViewModel.toId)
                .observe(viewLifecycleOwner, { terminal ->
                    terminal.name.also { statBinding.nameTo.text = it }
                    terminal.address.also { statBinding.addressTo.text = it }
                    "${BigDecimal(terminal.distanceValue/1000.0).setScale(2,RoundingMode.HALF_EVEN)} км".also { statBinding.distanceTo.text = it }
                    statBinding.workTableTo.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl)
                        .placeholder(R.drawable.sample).into(statBinding.imageViewTo)
                })
        }
        appBarLogic?.hide()

        return statBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Checking that both of terminals are not empty
        statBinding.saveBtn.isEnabled = navViewModel.fromId > 0 && navViewModel.toId > 0

        statBinding.from.setOnClickListener {
            val bundle = bundleOf("pointer" to 0)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment, bundle)

        }
        statBinding.to.setOnClickListener {
            val bundle = bundleOf("pointer" to 1)
            findNavController().navigate(R.id.action_statFragment_to_viewPagerFragment, bundle)
        }

        //Saving transaction's data
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

    override fun onDestroyView() {
        super.onDestroyView()
        _statBinding = null
    }
}