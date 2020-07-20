package com.litecode.synchroniseurapp

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.litecode.pictrumapp.RecyclerAdapter.RecyclerViewAdapter
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager
import com.litecode.synchroniseurapp.services.MJobScheduler
import com.marie.mutinga.kyetting.api.RecyclerPubModels

class MainActivity : AppCompatActivity() {

    //private val databaseManager = DatabaseManager.getDatabase(this)
    lateinit var layoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var pubList: ArrayList<RecyclerPubModels>

    private val JOB_ID = 10
    lateinit var jobScheduler: JobScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()

        pubList = ArrayList<RecyclerPubModels>()

        getDataFromLocalDatabase()

        initJobScheduler()
    }

    fun getDataFromLocalDatabase(){
        // Fetching all the publications from room database
        val databaseManager = DatabaseManager.getDatabase(this)
        var list = databaseManager.getPublicationDao().getAll()

        for(i in 0 until list.size){
            pubList.add(RecyclerPubModels(list.get(i).title, list.get(i).content, list.get(i).status))
            val adapter = RecyclerViewAdapter(this, pubList)
            recyclerView.adapter = adapter
        }
    }


    fun initJobScheduler() {
        jobScheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(
            JobInfo.Builder(JOB_ID, ComponentName(this, MJobScheduler::class.java))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setMinimumLatency(1000)
                .setPersisted(true)
                //.setOverrideDeadline(1000)
                .build()
        )
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "Job scheduled now", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Job error", Toast.LENGTH_SHORT).show()
        }
    }


    fun openEditData(v: View){
        val intent = Intent(this@MainActivity, AddDataActivity::class.java)
        startActivity(intent)
    }
}
