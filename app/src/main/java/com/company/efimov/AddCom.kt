package com.company.efimov

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.company.efimov.databinding.ActivityAddComBinding
import com.google.firebase.database.*

class AddCom : AppCompatActivity() {
    lateinit var binding: ActivityAddComBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddComBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addAdd.setOnClickListener {
            val com=CompanyModel(binding.addName.text.toString(),binding.addTarif.text.toString())
            Log.d(TAG,"Первое поле"+com.name)
            Log.d(TAG,"Второе поле поле"+com.tarif)
            database.child("Company").addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(!snapshot.child(binding.addName.text.toString()).exists()){
                        database.child("Company").child(binding.addName.text.toString()).setValue(com)
                        finish()
                    }
                    else{
                        Toast.makeText(this@AddCom, "Такая компания уже существует", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}