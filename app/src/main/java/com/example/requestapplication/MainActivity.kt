package com.example.requestapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.requestapplication.Entities.Post
import com.example.requestapplication.Fragments.DescriptionFragment
import com.example.requestapplication.Fragments.ListFragment

class MainActivity : AppCompatActivity(), Communicator {

    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showListFragment()
    }

    private fun showListFragment() {
        val transaction = manager.beginTransaction()
        val listFragment = ListFragment()
        transaction.replace(R.id.frame, listFragment)
        transaction.commit()
    }

    override fun passData(post: Post) {
        val bundle = Bundle()
        bundle.putParcelable("post", post)

        val transaction = this.supportFragmentManager.beginTransaction()
        val descriptionFragment = DescriptionFragment()
        descriptionFragment.arguments = bundle

        transaction.replace(R.id.frame, descriptionFragment)
        transaction.addToBackStack(null)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}