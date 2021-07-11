package bag.dev.biatestproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.TextView
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
import bag.dev.biatestproject.databinding.FragmentToBinding
import bag.dev.biatestproject.hideKeyboard

class ToFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _toBinding:FragmentToBinding ? = null
    private val toBinding get() = _toBinding!!
    private val adapter = ToListAdapter()
    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel: TerminalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toBinding = FragmentToBinding.inflate(inflater,container,false)

        //RcView
        toBinding.toRcView.adapter = adapter
        toBinding.toRcView.layoutManager = LinearLayoutManager(requireContext())

        //TerminalViewModel
        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
        terminalViewModel.readAllToData.observe(viewLifecycleOwner, {terminal ->
            adapter.setData(terminal)
        })

        return toBinding.root
    }



    inner class ToListAdapter : RecyclerView.Adapter<ToListAdapter.ViewHolder>() {

        private var terminalList = emptyList<Terminal>()

        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.toTextView)
            fun inflateViews(item:Terminal) {
                textView.text = item.name

                itemView.setOnClickListener{
                    navViewModel.toId = item.id
                    findNavController().navigateUp()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_to_text,parent,false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.sortByName -> {
                terminalViewModel.sortToDataByName.observe(viewLifecycleOwner, {terminal->
                    adapter.setData(terminal)
                })
                true
            }
            R.id.sortByDistance -> {
                terminalViewModel.sortToDataByDistance.observe(viewLifecycleOwner, {terminal->
                    adapter.setData(terminal)
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }

    private fun searchString(query:String){
        val searchQuery = "%$query%"

        terminalViewModel.searchToDatabase(searchQuery).observe(viewLifecycleOwner,{list->
            list.let {
                adapter.setData(it)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _toBinding = null

    }
}