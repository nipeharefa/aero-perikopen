package dev.nias.perikopen.adapter.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.nias.perikopen.R
import dev.nias.perikopen.model.ReadingList

class ReadingListAdapter(var items: MutableList<ReadingList>) : RecyclerView.Adapter<ReadingListAdapter.MyViewHolder>(){

    class MyViewHolder: RecyclerView.ViewHolder {
        lateinit var tvReading: TextView
        lateinit var tvReadingValue: TextView
        constructor(v : View) : super(v) {
            tvReading = v.findViewById(R.id.tv_event_detail_reading_item)
            tvReadingValue = v.findViewById(R.id.tv_event_detail_reading_item_value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.event_detail_reading_item, parent, false)

        return ReadingListAdapter.MyViewHolder(v)
//        R.layout.reading
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val current : ReadingList = items.get(position)

        holder.tvReading.text = current.bookName
        holder.tvReadingValue.text = current.bookValue
    }
}