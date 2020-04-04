package com.example.requestapplication.Fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.requestapplication.Communicator
import com.example.requestapplication.PostDatabase.AppDatabase
import com.example.requestapplication.PostDatabase.PostEntity
import com.example.requestapplication.Entities.CentroidCoordinates
import com.example.requestapplication.Entities.Post
import com.example.requestapplication.MyAdapter
import com.example.requestapplication.R
import com.example.requestapplication.Request
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment() {

    private var list: ListView? = null
    private var adapter: MyAdapter? = null
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val postsApi = retrofit.create(Request::class.java)
    private var db: AppDatabase? = null
    private val compos = CompositeDisposable()
    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post_list, container, false)
        list = view.findViewById(R.id.list)
        db = Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java, "database"
        ).build()
        comm = activity as Communicator
        setupListView()
        return view
    }

    private fun setupListView() {
        list!!.setOnItemClickListener { parent, view, position, id ->
            val element = adapter!!.getItem(position)!!
            comm.passData(post = element)
        }

        compos.add(
            postsApi.getAllPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    adapter = MyAdapter(activity!!.applicationContext, R.layout.row, result)
                    list?.adapter = adapter
                    saveDataToDB(posts = result)
                }, { error ->
                    showDataFromDB()
                    error.printStackTrace()
                })
        )
    }

    private fun saveDataToDB(posts: ArrayList<Post>) {
        compos.add(
            db!!.postDao().getCount()
                .subscribe({
                    if (it == 0) {
                        var counter = 0
                        posts.forEach {
                            val post =
                                PostEntity(
                                    primaryId = counter,
                                    lat = it.centroidCoordinates.lat,
                                    lon = it.centroidCoordinates.lon,
                                    caption = it.caption,
                                    image = it.image,
                                    identifier = it.identifier
                                )
                            db!!.postDao().insert(post = post)
                            counter++
                        }
                    }
                }, { error ->
                    error.printStackTrace()
                })
        )
    }

    private fun showDataFromDB() {
        compos.add(
            db!!.postDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.isEmpty()) {
                        showAlert()
                    } else {
                        val posts = ArrayList<Post>()
                        it.forEach {
                            val centroidCoordinates =
                                CentroidCoordinates(
                                    lat = it.lat,
                                    lon = it.lon
                                )
                            val post =
                                Post(
                                    centroidCoordinates = centroidCoordinates,
                                    identifier = it.identifier,
                                    caption = it.caption,
                                    image = it.image
                                )
                            posts.add(post)
                        }
                        adapter = MyAdapter(activity!!.applicationContext, R.layout.row, posts)
                        list?.adapter = adapter
                    }
                }, { error ->
                    error.printStackTrace()
                })
        )
    }

    private fun showAlert() {
        val dialogBuilder = AlertDialog.Builder(this.context!!)
        dialogBuilder.setMessage("List is empty")
            .setCancelable(false)
            .setPositiveButton("Proceed", DialogInterface.OnClickListener { dialog, id ->
                activity!!.finish()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Error")
        alert.show()
    }
}