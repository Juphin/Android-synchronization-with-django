package com.litecode.synchroniseurapp.services

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager

class MJobScheduler: JobService() {
    lateinit var context: Context
    lateinit var mJobExecutor: MJobExecutor
    lateinit var databaseManager: DatabaseManager


    override fun onStartJob(jobParameters: JobParameters?): Boolean {
        mJobExecutor = object: MJobExecutor(this){
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
                jobFinished(jobParameters, true)
            }
        }

        mJobExecutor.execute()
        return true
    }

    override fun onStopJob(jobParameters: JobParameters?): Boolean {
        mJobExecutor.cancel(true)
        return true
    }

}