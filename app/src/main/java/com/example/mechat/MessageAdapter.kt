package com.example.mechat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth


class MessageAdapter(private val context: Context, private val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val ITEM_RECEIVE=1
    private val ITEM_SENT=2



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType==2){
            val view:View=LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            return SendViewHolder(view)
        }
        else {
            val view:View=LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage=messageList[position]
        if(holder.javaClass==SendViewHolder::class.java){
            val viewHolder= holder as SendViewHolder
            viewHolder.sendMessage.text=currentMessage.message
        }
        else if(holder.javaClass==ReceiveViewHolder::class.java){
            val viewHolder=holder as ReceiveViewHolder
            viewHolder.receiveMessage.text=currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
              val currentMessage=messageList[position]
              if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
                  return ITEM_SENT
              }
             else{
                 return ITEM_RECEIVE
    }

    }
    class SendViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val sendMessage =itemView.findViewById<TextView>(R.id.sendMessage)
    }
    class ReceiveViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val receiveMessage=itemView.findViewById<TextView>(R.id.receiveMessage)
    }
}