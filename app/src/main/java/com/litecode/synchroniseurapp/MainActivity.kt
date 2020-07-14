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

class MainActivity : AppCompatActivity() {

    private val JOB_ID = 10
    lateinit var jobScheduler: JobScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initJobScheduler()
    }



    fun initJobScheduler() {
        jobScheduler = applicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = jobScheduler.schedule(
            JobInfo.Builder(JOB_ID, ComponentName(this, JobScheduler::class.java!!))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
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
