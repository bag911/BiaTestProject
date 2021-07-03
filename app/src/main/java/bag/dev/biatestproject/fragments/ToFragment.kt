package bag.dev.biatestproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bag.dev.biatestproject.NavViewModel
import bag.dev.biatestproject.R
import bag.dev.biatestproject.database.Terminal
import bag.dev.biatestproject.database.TerminalViewModel
import bag.dev.biatestproject.databinding.FragmentToBinding

class ToFragment : Fragment() {
    private var _toBinding:FragmentToBinding ? = null
    private val toBinding get() = _toBinding!!
    private val adapter = ToListAdapter(emptyArray())
    private val navViewModel: NavViewModel by activityViewModels()
    private lateinit var terminalViewModel: TerminalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toBinding = FragmentToBinding.inflate(inflater,container,false)

        //RcView
        toBinding.toRcView.adapter = adapter
        toBinding.toRcView.layoutManager = LinearLayoutManager(requireContext())

        //TerminalViewModel //Todo uncomment this
//        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
//        terminalViewModel.readAllData.observe(viewLifecycleOwner, Observer {terminal ->
//            adapter.setData(terminal)
//        })

        return toBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toBinding.toRcView.adapter = ToListAdapter(arrayOf("Racoon","Spoon","Chan"))
    }

    inner class ToListAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<ToListAdapter.ViewHolder>() {

        private var terminalList = emptyList<Terminal>()

        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.toTextView)
            fun inflateViews(item:String) {
                textView.text = item
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_to_text,parent,false)
            return ViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataSet[position]
            holder.inflateViews(item)
        }

        override fun getItemCount() = dataSet.size

        fun setData(terminal: List<Terminal>){
            this.terminalList = terminal
            notifyDataSetChanged()
        }

    }
}