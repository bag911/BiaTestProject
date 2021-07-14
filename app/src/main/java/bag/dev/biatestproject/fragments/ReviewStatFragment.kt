package bag.dev.biatestproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import bag.dev.biatestproject.R
import bag.dev.biatestproject.databinding.FragmentReviewStatBinding
import bag.dev.biatestproject.viewmodel.NavViewModel
import bag.dev.biatestproject.viewmodel.TerminalViewModel
import com.bumptech.glide.Glide
import java.math.BigDecimal
import java.math.RoundingMode

class ReviewStatFragment(private val position:Int) : Fragment() {

    private var _reviewStatBinding: FragmentReviewStatBinding? = null
    private val reviewStatBinding get() = _reviewStatBinding!!
    private lateinit var terminalViewModel: TerminalViewModel
    private val navViewModel: NavViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _reviewStatBinding = FragmentReviewStatBinding.inflate(inflater,container,false)

        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        onPause()

        return reviewStatBinding.root
    }

    override fun onResume() {
        super.onResume()
        when(position){
            0 -> inflateTerminalFrom()
            else -> inflateTerminalTo()
        }
    }

    private fun inflateTerminalFrom() {
        //Checking selected From terminals
        if (navViewModel.checkFromItemSelected()) {
            terminalViewModel.getTerminalById(navViewModel.fromId)
                .observe(viewLifecycleOwner, { terminal ->
                    terminal.name.also { reviewStatBinding.nameFrom.text = it }
                    terminal.address.also { reviewStatBinding.addressFrom.text = it }
                    "${BigDecimal(terminal.distanceValue/1000.0).setScale(2, RoundingMode.HALF_EVEN)} км".also { reviewStatBinding.distanceFrom.text = it }
                    reviewStatBinding.workTableFrom.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl)
                        .placeholder(R.drawable.placeholder_img).into(reviewStatBinding.imageViewFrom)
                })
        }

    }

    private fun inflateTerminalTo() {
        //Checking selected To terminals
        if (navViewModel.checkToItemSelected()) {
            terminalViewModel.getTerminalById(navViewModel.toId)
                .observe(viewLifecycleOwner, { terminal ->
                    terminal.name.also { reviewStatBinding.nameFrom.text = it }
                    terminal.address.also { reviewStatBinding.addressFrom.text = it }
                    "${BigDecimal(terminal.distanceValue/1000.0).setScale(2, RoundingMode.HALF_EVEN)} км".also { reviewStatBinding.distanceFrom.text = it }
                    reviewStatBinding.workTableFrom.text = terminal.worktables
                    Glide.with(this).load(terminal.mapUrl)
                        .placeholder(R.drawable.placeholder_img).into(reviewStatBinding.imageViewFrom)
                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _reviewStatBinding = null
    }


}