package bag.dev.biatestproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import bag.dev.biatestproject.R
import bag.dev.biatestproject.databinding.FragmentStatBinding
import bag.dev.biatestproject.room.model.Transactions
import bag.dev.biatestproject.viewmodel.NavViewModel
import bag.dev.biatestproject.viewmodel.TerminalViewModel
import com.google.android.material.tabs.TabLayoutMediator


class StatFragment : Fragment() {

    private var _statBinding: FragmentStatBinding? = null
    private val statBinding get() = _statBinding!!
    private lateinit var adapter:ViewPagerStatAdapter

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
        //ViewPager
        val tabLayout = statBinding.tabLayout
        val viewPager = statBinding.statViewPager
        adapter = ViewPagerStatAdapter(requireActivity())
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->                                  //Connect TabLayout to ViewPager
            when(position){
                0 -> tab.text = "???????????????? ????????????"
                else -> tab.text = "???????????????? ????????"
            }
        }.attach()
        viewPager.currentItem = 0

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
    class ViewPagerStatAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> ReviewStatFragment(0)
                else -> ReviewStatFragment(1)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _statBinding = null
    }
}