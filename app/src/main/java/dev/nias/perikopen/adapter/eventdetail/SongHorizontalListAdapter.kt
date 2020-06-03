package dev.nias.perikopen.adapter.eventdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.nias.perikopen.R
import dev.nias.perikopen.model.song.ISong
import kotlinx.android.synthetic.main.rv_event_detail_songs.view.*

class SongHorizontalListAdapter(var items : List<ISong>) : RecyclerView.Adapter<SongHorizontalListAdapter.MyViewHolder>() {

    class MyViewHolder : RecyclerView.ViewHolder {
        lateinit var tvSongNumber : TextView
        constructor(v: View) : super(v) {
            tvSongNumber = v.findViewById(R.id.tv_event_detail_song_item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.rv_event_detail_songs_item, parent, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = items.get(position)

        holder.tvSongNumber.text = current.songNumber
    }
}