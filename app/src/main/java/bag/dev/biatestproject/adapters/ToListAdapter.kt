package bag.dev.biatestproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bag.dev.biatestproject.NavViewModel
import bag.dev.biatestproject.R

class ToListAdapter(private val dataSet: Array<String>) : RecyclerView.Adapter<ToListAdapter.ViewHolder>() {
    class ViewHolder (view:View): RecyclerView.ViewHolder(view){
        private val textView:TextView = view.findViewById(R.id.toTextView)
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

}