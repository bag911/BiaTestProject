package bag.dev.biatestproject.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import bag.dev.biatestproject.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator


class ViewPagerFragment : Fragment() {

    private var _viewPagerBinding:FragmentViewPagerBinding? = null
    private val viewPagerBinding get() = _viewPagerBinding!!

    private var appBarLogic:AppBarLogic ?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appBarLogic = context as AppBarLogic
        appBarLogic?.show()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewPagerBinding = FragmentViewPagerBinding.inflate(inflater,container,false)

        //ViewPager
        val tabLayout = viewPagerBinding.tabLayout
        val viewPager = viewPagerBinding.pager

        viewPager.adapter = ViewPagerAdapter(requireActivity())
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        TabLayoutMediator(tabLayout, viewPager) { tab, position ->                                  //Connect TabLayout to ViewPager
            when(position){
                0 -> tab.text = "Откуда"
                else -> tab.text = "Куда"
            }
        }.attach()

        viewPager.currentItem = arguments?.getInt("pointer")!!

        return viewPagerBinding.root
    }


    //Adapter
    class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> FromFragment()
                else -> ToFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewPagerBinding = null

    }



}


