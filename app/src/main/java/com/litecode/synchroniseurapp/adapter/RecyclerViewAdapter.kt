package com.litecode.pictrumapp.RecyclerAdapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.litecode.synchroniseurapp.R
import com.litecode.synchroniseurapp.models.Constracts
import com.marie.mutinga.kyetting.api.RecyclerPubModels

class RecyclerViewAdapter (private val context: Context, private val dataList: ArrayList<RecyclerPubModels>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: RecyclerPubModels = dataList[position]

        holder.title.text = currentItem.title
        holder.content.text = currentItem.content
        val status = currentItem.status

        if(status == Constracts.SYNC_STATUE_OKAY){
            holder.status.setImageResource(R.drawable.status_ok)
        }else{
            holder.status.setImageResource(R.drawable.status_failed)
        }
    }


    // Defining my ViewHolder class here
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var title: TextView = view.findViewById(R.id.title)
        var content: TextView = view.findViewById(R.id.content)
        var status: ImageView = view.findViewById(R.id.status)
    }
}