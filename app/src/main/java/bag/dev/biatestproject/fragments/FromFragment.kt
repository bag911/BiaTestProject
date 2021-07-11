package bag.dev.biatestproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bag.dev.biatestproject.NavViewModel
import bag.dev.biatestproject.R
import bag.dev.biatestproject.database.Terminal
import bag.dev.biatestproject.database.TerminalViewModel
import bag.dev.biatestproject.databinding.FragmentFromBinding
import bag.dev.biatestproject.hideKeyboard

class FromFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _fromBinding:FragmentFromBinding ? = null
    private val fromBinding get() = _fromBinding!!
    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel:TerminalViewModel
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
            adapter.setData(terminal)
        })

        return fromBinding.root
    }

    inner class FromListAdapter : RecyclerView.Adapter<FromListAdapter.ViewHolder>() {

        private var terminalList = emptyList<Terminal>()

        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.fromTextView)
            fun inflateViews(item:Terminal) {
                textView.text = item.name

                itemView.setOnClickListener{
                    navViewModel.fromId = item.id
                    findNavController().navigateUp()
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

        fun setData(terminal: List<Terminal>){
            this.terminalList = terminal
            notifyDataSetChanged()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true

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

    private fun searchString(query:String){
        val searchQuery = "%$query%"

        terminalViewModel.searchFromDatabase(searchQuery).observe(viewLifecycleOwner,{list->
            list.let {
                adapter.setData(it)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sortByName -> {
                terminalViewModel.sortFromDataByName.observe(viewLifecycleOwner, {terminal->
                    adapter.setData(terminal)
                })
                true
            }
            R.id.sortByDistance -> {
                terminalViewModel.sortFromDataByDistance.observe(viewLifecycleOwner, {terminal->
                    adapter.setData(terminal)
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}