package dev.nias.perikopen.adapter.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.nias.perikopen.R
import dev.nias.perikopen.model.Perikopen
import java.text.SimpleDateFormat


class PerikopenEventAdapter(var items: MutableList<Perikopen>) : RecyclerView.Adapter<PerikopenEventAdapter.ViewHolder>(){
    class ViewHolder : RecyclerView.ViewHolder {
        lateinit var weekName: TextView
        lateinit var dateText: TextView
        constructor(v: View): super(v) {

            weekName = v.findViewById(R.id.home_perikopen_week_name)
            dateText = v.findViewById(R.id.home_perikopen_date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_home_event_perikopen, parent, false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dateFormatted = SimpleDateFormat("EEEE, dd MMMM yyyy")

        val formatted = dateFormatted.format(items.get(position).date)
        holder.weekName.text = items.get(position).weekName
        holder.dateText.text = formatted

        holder.itemView.setOnClickListener {
            Log.i("EventList", "clicked")
        }
    }
}
