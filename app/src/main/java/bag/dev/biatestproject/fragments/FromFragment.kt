package bag.dev.biatestproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bag.dev.biatestproject.R
import bag.dev.biatestproject.databinding.FragmentFromBinding
import bag.dev.biatestproject.hideKeyboard
import bag.dev.biatestproject.room.model.Terminal
import bag.dev.biatestproject.viewmodel.NavViewModel
import bag.dev.biatestproject.viewmodel.TerminalViewModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.math.BigDecimal
import java.math.RoundingMode

class FromFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _fromBinding:FragmentFromBinding ? = null
    private val fromBinding get() = _fromBinding!!

    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel: TerminalViewModel
    private val adapter = FromListAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fromBinding = FragmentFromBinding.inflate(inflater,container,false)

        //RcView
        fromBinding.fromRcView.adapter = adapter
        fromBinding.fromRcView.layoutManager = LinearLayoutManager(requireContext())

        //TerminalViewModel
        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        terminalViewModel.readAllFromData.observe(viewLifecycleOwner, {terminal ->
            adapter.setData(terminal)                                                               //Fill list by "from" data
        })

        return fromBinding.root
    }

    //FromFragment Recycler View adapter
    inner class FromListAdapter : RecyclerView.Adapter<FromListAdapter.ViewHolder>() {

        private var terminalList = emptyList<Terminal>()

        //Adapter ViewHolder
        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textName: TextView = view.findViewById(R.id.fromTextView)
            private val imageView: CircleImageView = view.findViewById(R.id.circleMapImage)
            private val textDistance: TextView = view.findViewById(R.id.textDistance)
            fun inflateViews(item: Terminal) {
                textName.text = item.name
                Glide.with(requireContext()).load(item.mapUrl).circleCrop()
                    .placeholder(R.drawable.placeholder_img).circleCrop().into(imageView)
                "${BigDecimal(item.distanceValue/1000.0).setScale(2,RoundingMode.HALF_EVEN)} км".also { textDistance.text = it }
                itemView.setOnClickListener{
                    navViewModel.fromId = item.id
                    findNavController().navigate(R.id.action_viewPagerFragment_to_statFragment)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_from_text,parent,false)
            return ViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val currentItem = terminalList[position]
            holder.inflateViews(currentItem)
        }

        override fun getItemCount() = terminalList.size

        fun setData(terminal: List<Terminal>){                                                      //Filling List of terminals
            this.terminalList = terminal
            notifyDataSetChanged()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true


        menu.findItem(R.id.sortByName).setOnMenuItemClickListener{
            terminalViewModel.sortFromDataByName.observe(viewLifecycleOwner, {terminal->
                adapter.setData(terminal)                                                       //Filling list of Terminals with sorted data alphabetically
            })
            true
        }
        menu.findItem(R.id.sortByDistance).setOnMenuItemClickListener {
            terminalViewModel.sortFromDataByDistance.observe(viewLifecycleOwner, {terminal->
                adapter.setData(terminal)                                                       //Filling list of Terminals with sorted data by distance
            })
            true
        }

        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchString(query)
            hideKeyboard()
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchString(query)
        }
        return true
    }

    //Search string method
    private fun searchString(query:String){
        val searchQuery = "%$query%"

        terminalViewModel.searchFromDatabase(searchQuery).observe(viewLifecycleOwner,{list->
            list.let {
                adapter.setData(it)                                                                 //Filling list of Terminals that match the string
            }
        })
    }

    //Options Menu Listener
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when(item.itemId){
//            R.id.sortByName -> {
//                terminalViewModel.sortFromDataByName.observe(viewLifecycleOwner, {terminal->
//                    adapter.setData(terminal)                                                       //Filling list of Terminals with sorted data alphabetically
//                })
//                true
//            }
//            R.id.sortByDistance -> {
//                terminalViewModel.sortFromDataByDistance.observe(viewLifecycleOwner, {terminal->
//                    adapter.setData(terminal)                                                       //Filling list of Terminals with sorted data by distance
//                })
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("DOTA", "Destroy")
        _fromBinding = null
        onDestroyOptionsMenu()
    }


}