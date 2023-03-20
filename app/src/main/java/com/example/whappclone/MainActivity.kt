package com.example.whappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.whappclone.Activity.NumberActivity
import com.example.whappclone.adapter.ViewPagerAdapter
import com.example.whappclone.databinding.ActivityMainBinding
import com.example.whappclone.ui.Call_Fragment
import com.example.whappclone.ui.Chat_Fragment
import com.example.whappclone.ui.Status_Fragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding ?= null
    private  lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)



        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            startActivity(Intent(this@MainActivity  , NumberActivity::class.java))
            finish()
        }


        val fragmentArrayList = ArrayList<Fragment>()

        fragmentArrayList.add(Chat_Fragment())
        fragmentArrayList.add(Status_Fragment())
        fragmentArrayList.add(Call_Fragment())

        val adapter = ViewPagerAdapter(this@MainActivity ,supportFragmentManager, fragmentArrayList)

        binding!!.viewPager.adapter = adapter

        binding!!.tabs.setupWithViewPager(binding!!.viewPager)


      //  MainActivity.= DividerItemDecoration(this, RecyclerView.VERTICAL)



    }
}