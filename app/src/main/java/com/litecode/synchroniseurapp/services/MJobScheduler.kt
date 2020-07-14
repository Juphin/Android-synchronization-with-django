package com.litecode.synchroniseurapp.services

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MJobScheduler: JobService() {
    lateinit var context: Context
    lateinit var MJobExecutor: MJobExecutor
    lateinit var databaseManager: DatabaseManager

    override fun onStopJob(jobParameters: JobParameters?): Boolean {
        MJobExecutor = @SuppressLint("StaticFieldLeak")
        object: MJobExecutor(this){
            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
                jobFinished(jobParameters, true)
            }
        }

        MJobExecutor.execute()
        return true
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        MJobExecutor.cancel(true)
        return true
    }
}