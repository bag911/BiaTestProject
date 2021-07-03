package bag.dev.biatestproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bag.dev.biatestproject.NavViewModel
import bag.dev.biatestproject.R
import bag.dev.biatestproject.database.Terminal
import bag.dev.biatestproject.databinding.FragmentFromBinding

class FromFragment : Fragment() {

    private var _fromBinding:FragmentFromBinding ? = null
    private val fromBinding get() = _fromBinding!!
    private val navViewModel: NavViewModel by activityViewModels()
    private val adapter = FromListAdapter(emptyArray())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fromBinding = FragmentFromBinding.inflate(inflater,container,false)

        //RcView
        fromBinding.fromRcView.adapter = adapter
        fromBinding.fromRcView.layoutManager = LinearLayoutManager(requireContext())

        //TerminalViewModel //Todo uncomment this
//        terminalViewModel = ViewModelProvider(this).get(TerminalViewModel::class.java)
//        terminalViewModel.readAllData.observe(viewLifecycleOwner, Observer {terminal ->
//            adapter.setData(terminal)
//        })

        return fromBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromBinding.fromRcView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        fromBinding.fromRcView.adapter = FromListAdapter(arrayOf("Sun","Moon","Chan"))
    }

    inner class FromListAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<FromListAdapter.ViewHolder>() {

        private var terminalList = emptyList<Terminal>()

        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.fromTextView)
            fun inflateViews(item:String) {
                textView.text = item

                itemView.setOnClickListener {
//                    navViewModel.fromId = item.id
                    findNavController().navigate(R.id.action_viewPagerFragment_to_statFragment)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_from_text,parent,false)
            return ViewHolder(inflatedView)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = dataSet[position]
            holder.inflateViews(item)
        }

        override fun getItemCount() = dataSet.size

        fun setData(terminal:List<Terminal>){
            this.terminalList = terminal
            notifyDataSetChanged()
        }
    }
}