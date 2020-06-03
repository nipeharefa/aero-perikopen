package dev.nias.perikopen.adapter.eventdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.nias.perikopen.R
import dev.nias.perikopen.model.song.BookInterface
import kotlinx.android.synthetic.main.rv_event_detail_songs.view.*

class SongAdapter(var items : List<BookInterface>) : RecyclerView.Adapter<SongAdapter.MyViewHolder> (){

    class MyViewHolder : RecyclerView.ViewHolder {

        lateinit var tvSongBookName : TextView
        lateinit var rvSongHorizontalList : RecyclerView
        constructor(v: View) : super(v) {
            tvSongBookName = v.findViewById(R.id.tv_event_detail_song_book_name)
            rvSongHorizontalList = v.findViewById(R.id.rv_event_detail_song_horizontal_list)

            val l = LinearLayoutManager(v.context)
            l.orientation = LinearLayoutManager.HORIZONTAL

            rvSongHorizontalList.layoutManager= l
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_event_detail_songs, parent, false)

        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = items.get(position)

        Log.i(current.getBookName(), current.songs.toString())
        val songListHorizontal = SongHorizontalListAdapter(current.songs)

        holder.rvSongHorizontalList.adapter = songListHorizontal

        holder.tvSongBookName.text = current.getBookName()
    }
}