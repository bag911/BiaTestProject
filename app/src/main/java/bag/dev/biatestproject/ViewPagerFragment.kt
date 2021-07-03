package bag.dev.biatestproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import bag.dev.biatestproject.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*


class ViewPagerFragment : Fragment() {

    private var _viewPagerBinding:FragmentViewPagerBinding? = null
    private val viewPagerBinding get() = _viewPagerBinding!!

//    private val navViewModel:NavViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewPagerBinding = FragmentViewPagerBinding.inflate(inflater,container,false)


        val tabLayout = viewPagerBinding.tabLayout
        val viewPager = viewPagerBinding.pager

        viewPager.adapter = ViewPagerAdapter(requireActivity())
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 -> tab.text = "Откуда"
                else -> tab.text = "Куда"
            }
        }.attach()

        viewPager.currentItem = arguments?.getInt("pointer")!!


        return viewPagerBinding.root
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> FromFragment()
                else -> ToFragment()
            }
        }
    }
}


