package com.example.mechat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError

import com.google.firebase.database.DataSnapshot

import com.google.firebase.database.ValueEventListener




class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
   private lateinit var userList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        mDbRef=FirebaseDatabase.getInstance().getReference()
        userList= ArrayList()
        userAdapter= UserAdapter(this,userList)
        recyclerView=findViewById(R.id.userRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.adapter=userAdapter
        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in dataSnapshot.children){
                    val currentUser=postSnapshot.getValue(User::class.java)
                    if(auth.currentUser?.uid!=currentUser?.uid){
                        userList.add(currentUser!!)
                    }

                }
                userAdapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
// Failed to read value

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if(item.itemId==R.id.logout){
           val intent= Intent(this,LoginActivity::class.java)
           startActivity(intent)
           auth.signOut()
           return true
       }

        return true
    }
}