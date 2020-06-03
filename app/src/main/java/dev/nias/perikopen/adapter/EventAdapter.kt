package dev.nias.perikopen.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.nias.perikopen.R
import dev.nias.perikopen.adapter.home.PerikopenEventAdapter
import dev.nias.perikopen.model.HomeEvent

class EventAdapter(var items : MutableList<HomeEvent>) : RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    class MyViewHolder : RecyclerView.ViewHolder {
        lateinit var weekName : TextView
        lateinit var rvPerikopen: RecyclerView
        constructor(v : View) : super(v) {
            weekName = v.findViewById(R.id.home_rv_events_content_week_name)
            rvPerikopen = v.findViewById(R.id.rv_home_perikopen)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v  = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_home_upcomings_events_content, parent, false)


        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.weekName.text = items.get(position).name
        val perikopenAdapter = PerikopenEventAdapter(items.get(position).perikopens)

        holder.rvPerikopen.adapter = perikopenAdapter
        holder.rvPerikopen.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

}