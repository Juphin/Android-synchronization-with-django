package com.litecode.synchroniseurapp

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.litecode.synchroniseurapp.Models.MyContract
import com.litecode.synchroniseurapp.roomDatabaseManager.DatabaseManager
import com.litecode.synchroniseurapp.roomDatabaseManager.PubTable

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


    fun submitData(v: View){
        saveToLocalDatabase()
    }


    private fun saveToLocalDatabase() {
        val databaseManager = DatabaseManager.getDatabase(this)
        val pubDao = databaseManager.getPublicationDao()
        val myTitle = title.text.toString()
        val myContent = content.text.toString()
        if (myTitle != "" && myContent != "") {
            val id = pubDao.insert(PubTable(0, myTitle, myContent, 1))
            title.setText("")
            content.setText("")

            Toast.makeText(this@AddDataActivity, "Donnée enregistré avec success, id : $id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkNetworkConnection(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
