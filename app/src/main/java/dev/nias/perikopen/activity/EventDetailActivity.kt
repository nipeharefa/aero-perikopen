package dev.nias.perikopen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dev.nias.perikopen.FindOnePerikopenByIdQuery
import dev.nias.perikopen.GraphQL
import dev.nias.perikopen.R
import dev.nias.perikopen.adapter.eventdetail.ReadingListAdapter
import dev.nias.perikopen.adapter.eventdetail.SongAdapter
import dev.nias.perikopen.model.ReadingList
import dev.nias.perikopen.model.song.Book
import dev.nias.perikopen.model.song.BukuZinuno
import dev.nias.perikopen.model.song.KidungJemaat
import java.text.SimpleDateFormat

class EventDetailActivity : AppCompatActivity() {

    lateinit var apolloClient: ApolloClient
    lateinit var tvWeekName : TextView
    lateinit var tvEventName: TextView
    lateinit var songsAdapter: SongAdapter
    lateinit var rvSongs : RecyclerView
    lateinit var tvEventDate: TextView
    lateinit var readingListAdapter: ReadingListAdapter
    lateinit var rvReadingList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        //actionbar
        val actionbar = supportActionBar
        actionbar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = ""
        }

        apolloClient = GraphQL.getClient()

        val intent = getIntent()
        val perikopenId = intent.getStringExtra("perikopenId")

        songsAdapter = SongAdapter(mutableListOf())
        readingListAdapter = ReadingListAdapter(mutableListOf())

        // Songs RecycleView
        rvSongs = findViewById(R.id.rv_event_detail_songs)
        rvSongs.adapter = songsAdapter
        rvSongs.layoutManager = LinearLayoutManager(this)

        // ReadingList RecycleView

        rvReadingList = findViewById(R.id.rv_event_detail_reading_list)
        rvReadingList.adapter = readingListAdapter
        rvReadingList.layoutManager = LinearLayoutManager(this)

        tvWeekName = findViewById(R.id.tv_event_detail_week_name)
        tvEventName = findViewById(R.id.tv_event_detail_event_name)
        tvEventDate = findViewById(R.id.tv_event_detail_date_string)

        perikopenId?.let {

            var q = FindOnePerikopenByIdQuery(perikopenId)

            apolloClient.query(q).enqueue(object : ApolloCall.Callback<FindOnePerikopenByIdQuery.Data>() {
                override fun onResponse(response: Response<FindOnePerikopenByIdQuery.Data>) {
                    Log.i("event", "sukses")

                    val perikopen = response.data?.findOneById

                    val readingLists =  mutableListOf<ReadingList>()

                    perikopen?.let {
                        it.fragments?.perikopenDetail.let {

                            val books = mutableListOf<Book>()
                            val kj = Book("Kidung Jemaat")
                            val bz = Book("Buku Zinuno")
                            val bn = Book("Buku Naino")

                            it.kidungJemaat.let {
                                it?.forEach{
                                    it?.fragments?.nameParts?.let {
                                        kj.songs.add(KidungJemaat(it.id, it.songNumber))
                                    }
                                }
                            }

                            it.bukuZinuno?.let {
                                it?.forEach{
                                    it?.fragments?.nameParts?.let {
                                        bz.songs.add(BukuZinuno(it.id, it.songNumber))
                                    }
                                }
                            }

                            it.bukuNaino?.let {
                                it?.forEach{
                                    it?.fragments?.nameParts?.let {
                                        bn.songs.add(BukuZinuno(it.id, it.songNumber))
                                    }
                                }
                            }

                            it.readList.forEach {
                                var readList = ReadingList(id = it?.id?: "")
                                readList.bookName = it?.bookName?: ""
                                readList.bookValue = it?.bookValue?: ""
                                readList.displayOrder = it?.displayOrder?: 0

                                readingLists.add(readList)
                            }

                            books.add(kj)
                            books.add(bz)
                            books.add(bn)

                            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

                            readingListAdapter.items = readingLists

                            val activePerikopenDate = format.parse(it.date)
                            runOnUiThread {
                                tvWeekName.text = it.weekName
                                tvEventName.text = it.eventGroup.name
                                tvEventDate.text = SimpleDateFormat("EEEE, dd MMMM yyyy").format(activePerikopenDate)

                                songsAdapter.items = books
                                songsAdapter.notifyDataSetChanged()
                                readingListAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                override fun onFailure(e: ApolloException) {
                   Log.i("event", "gagal")
                }
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
