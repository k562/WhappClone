package com.example.whappclone.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whappclone.Activity.modal.MessageModel
import com.example.whappclone.R
import com.example.whappclone.adapter.MessageAdapter
import com.example.whappclone.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity(){

     private lateinit var binding : ActivityChatBinding
     private lateinit var database: FirebaseDatabase

     private lateinit var senderUid: String
     private lateinit var receiverUid : String

     private lateinit var senderRoom : String

     private lateinit var receiverRoom : String

     private lateinit var list : ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        list = ArrayList()

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid





        database = FirebaseDatabase.getInstance()


        binding.ImgSend.setOnClickListener {

            if (binding.EdtMsgBox.text.isEmpty()){
                Toast.makeText(this@ChatActivity, "Please enter a message", Toast.LENGTH_SHORT).show()

            } else {

                val message = MessageModel(binding.EdtMsgBox.text.toString() , senderUid , Date().time)

                val randomKey = database.reference.push().key

                database.reference.child("chats")
                    .child(senderRoom).child("message")
                    .child(randomKey!!).setValue(message)
                    .addOnSuccessListener {

                        database.reference.child("chats")
                            .child(receiverRoom).child("message")
                            .child(randomKey!!).setValue(message).addOnSuccessListener {

                                binding.EdtMsgBox.text = null
                               Toast.makeText(this@ChatActivity, "Message sent", Toast.LENGTH_SHORT).show()


                            }
                    }

            }
        }


        database.reference.child("chats")
            .child(senderRoom).child("message")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    list.clear()

                    for (snapshot1 in snapshot.children){

                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)

                    }

                    binding.RecyclerView2.adapter = MessageAdapter(this@ChatActivity, list)
                }

                override fun onCancelled(error: DatabaseError) {
                 //   Toast.makeText(this@ChatActivity, error.message, Toast.LENGTH_SHORT).show()
                    Toast.makeText(this@ChatActivity, "Error : $error", Toast.LENGTH_SHORT).show()
                }
            })


    }
}