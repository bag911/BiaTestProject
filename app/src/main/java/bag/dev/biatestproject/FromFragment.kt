package bag.dev.biatestproject

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
import bag.dev.biatestproject.databinding.FragmentFromBinding


class FromFragment : Fragment() {

    private var _fromBinding:FragmentFromBinding ? = null
    private val fromBinding get() = _fromBinding!!
    private val navViewModel:NavViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fromBinding = FragmentFromBinding.inflate(inflater,container,false)
        return fromBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromBinding.fromRcView.layoutManager = LinearLayoutManager(activity?.applicationContext)
        fromBinding.fromRcView.adapter = FromListAdapter(arrayOf("Sun","Moon","Chan"))
    }

    inner class FromListAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<FromListAdapter.ViewHolder>() {
        inner class ViewHolder (view:View): RecyclerView.ViewHolder(view){
            private val textView: TextView = view.findViewById(R.id.fromTextView)
            fun inflateViews(item:String) {
                textView.text = item

                itemView.setOnClickListener {
                    navViewModel.title = item
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

    }
}