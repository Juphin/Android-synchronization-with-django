package com.litecode.synchroniseurapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager
import com.litecode.synchroniseurapp.roomDatabaseManager.PubTable
import com.marie.mutinga.kyetting.api.PubModels
import com.marie.mutinga.kyetting.api.PubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDataActivity : AppCompatActivity() {

    //private lateinit var databaseManager: DatabaseManager
    private lateinit var title: EditText
    private lateinit var content: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        title = findViewById(R.id.title)
        content = findViewById(R.id.content)
    }


    fun sendDataToServer(v: View){
        val myTitle = title.text.toString()
        val myContent = content.text.toString()

        val service = PubService.create()
        val pubRequest = service.sendDataOrSync(myTitle, myContent)

        if(checkNetworkConnection()){
            //Calling methods of retrofit for handling data
            pubRequest.enqueue(object: Callback<PubModels>{
                override fun onResponse(call: Call<PubModels>, response: Response<PubModels>) {
                    if(response.code() == 201){
                        saveToLocalDatabase(0)
                    }else{
                        saveToLocalDatabase(1)
                        Toast.makeText(this@AddDataActivity, response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PubModels>, t: Throwable) {
                    saveToLocalDatabase(1)
                    Toast.makeText(this@AddDataActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }else{
            // No connexion available, Saving the data to the local database
            saveToLocalDatabase(1)
        }
    }



    private fun saveToLocalDatabase(status: Int) {
        val databaseManager = DatabaseManager.getDatabase(this)
        val pubDao = databaseManager.getPublicationDao()
        val myTitle = title.text.toString()
        val myContent = content.text.toString()
        if (myTitle != "" && myContent != "") {
            val id = pubDao.insert(PubTable(0, myTitle, myContent, status))
            title.setText("")
            content.setText("")
            Toast.makeText(this@AddDataActivity, "Data saved successfully, id : $id", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@AddDataActivity, "All fields are required", Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkNetworkConnection(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
