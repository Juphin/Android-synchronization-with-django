package com.litecode.synchroniseurapp.services

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.widget.Toast
import com.litecode.synchroniseurapp.models.Constracts
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager
import com.marie.mutinga.kyetting.api.PubModels
import com.marie.mutinga.kyetting.api.PubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class MJobExecutor(private val context: Context) : AsyncTask<Void, Void, String>() {

    private var str = ""

    override fun doInBackground(vararg voids: Void): String {
        // Fetching all the publications from room database
        val databaseManager = DatabaseManager.getDatabase(context)
        var list = databaseManager.getPublicationDao().getAll()
        if(checkNetworkConnection()){
            for(i in 0 until list.size){
                val sync_status = list.get(i).status
                if(sync_status == Constracts.SYNC_STATUS_FAILED){
                     val title = list.get(i).title.toString()
                     val content = list.get(i).content.toString()
                     // Calling the retrofit functionality to sync data to the server
                     val service = PubService.create() // Retrofit call service
                     val pubRequest = service.sendDataOrSync(title, content)
                      pubRequest.enqueue(object: Callback<PubModels> {
                          override fun onResponse(call: Call<PubModels>, response: Response<PubModels>) {
                              if(response.code() == 201){
                                    // Updating the local database sync status field
                                    // This call will update the status field after the data has been synced
                                   databaseManager
                                       .getPublicationDao()
                                       .updatePublicationwithPrecision(list[i].id)
                                  str = "Syncing data success"
                                }else{
                                    // In case an error occurs
                                Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT).show()
                                }
                            }

                          override fun onFailure(call: Call<PubModels>, t: Throwable) {
                              // In case an error occurs
                              str = "Syncing data failed"
                        }
                    })
                }
            }
        }else{
            str = "No connexion data detected"
        }
        return str
    }


    private fun checkNetworkConnection(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}