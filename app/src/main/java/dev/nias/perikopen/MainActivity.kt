package dev.nias.perikopen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import dev.nias.perikopen.activity.Disclaimer
import dev.nias.perikopen.adapter.EventAdapter
import dev.nias.perikopen.model.HomeEvent
import dev.nias.perikopen.model.Perikopen
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.util.*
import java.util.logging.Logger


class MainActivity : AppCompatActivity(), OnSuccessResponse {

    lateinit var upcomingEvents : MutableList<HomeEvent>
    lateinit var rvUpcomingEvents: RecyclerView
    lateinit var upcomingEventsAdapter : EventAdapter

    companion object {
        val LOG: Logger = Logger.getLogger(MainActivity::class.java.name)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //actionbar
        val actionbar = supportActionBar
        actionbar?.let {
            it.setDisplayHomeAsUpEnabled(false)
        }

        upcomingEvents = mutableListOf()

        upcomingEventsAdapter = EventAdapter(upcomingEvents)

        upcomingEventsAdapter.notifyDataSetChanged()

        rvUpcomingEvents = findViewById<RecyclerView>(R.id.home_rv_events)
        rvUpcomingEvents.adapter = upcomingEventsAdapter
        rvUpcomingEvents.layoutManager = LinearLayoutManager(this)

        val client = ApolloClient.builder()
            .serverUrl("https://perikopen-gql-v2.nias.dev/query")
            .build()

        val a = FeedQuery("2021-01-31")

        client
            .query(a)
            .enqueue(object : ApolloCall.Callback<FeedQuery.Data>() {
                override fun onResponse(response: Response<FeedQuery.Data>) {
                    val feeds = response.data?.getUpcomingEventGroup

                    runOnUiThread {
                        feeds?.forEach {
                            var h = HomeEvent(name = "")
                            it?.let {
//                                upcomingEvents.add(HomeEvent(it.name))
                                h.name = it.name

                                var listPerikopens = mutableListOf<Perikopen>()

                               it.perikopens?.forEach {
                                   val p = Perikopen(id = it?.id!!, weekName = it?.weekName!!)

                                  val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

                                   p.date = format.parse(it.date)
                                   listPerikopens.add(p)
                               }

                                Log.i("Main", listPerikopens.size.toString())
                                Log.i("Main", listPerikopens.toString())

                                h.perikopens = listPerikopens
                                upcomingEvents.add(h)
                            }

                        }

                        upcomingEventsAdapter.items = upcomingEvents
                        upcomingEventsAdapter.notifyDataSetChanged()
                    }

                }

                override fun onFailure(e: ApolloException) {
                    Log.e("MainActivity", e.message)
                }
            })

        val imgDisclaimerBtn : ImageButton = findViewById(R.id.imgBtnDisclaimer)

        imgDisclaimerBtn.setOnClickListener {
            Log.i("Main", "img")
            val i : Intent = Intent(it.context, Disclaimer::class.java)
            it.context.startActivity(i)
        }
    }

    override fun onSuccessResponse(response: Response<FeedQuery.Data>) {
        upcomingEvents.add(HomeEvent("nipeharefa"))
    }
}
