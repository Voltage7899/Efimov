package com.company.efimov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.efimov.databinding.ActivityMainBinding
import com.company.efimov.databinding.ActivityYourKvitanciaBinding
import com.google.firebase.database.FirebaseDatabase

class YourKvitancia : AppCompatActivity() {
    lateinit var binding: ActivityYourKvitanciaBinding
    var ListAdapter:AdapterKvitancia?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourKvitanciaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomMenuYourKvitancia.selectedItemId=R.id.kvitancia
        binding.bottomMenuYourKvitancia.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin -> {
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
                R.id.companies->{
                    val admin = Intent(this, Companies::class.java)
                    startActivity(admin)
                }
                R.id.indication->{
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }
            }

            true
        }
        binding.recKvitancia.layoutManager= LinearLayoutManager(this)
        ListAdapter = AdapterKvitancia()
        binding.recKvitancia.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<KvitanciaModel>{



        val List=ArrayList<KvitanciaModel>()
        database.getReference("Kvitan").child(userModel.currentuser?.phone.toString()).get().addOnSuccessListener {
            for (data in it.children){
                var kv=data.getValue(KvitanciaModel::class.java)
                if(kv!=null){
                    List.add(kv)

                }


            }
            ListAdapter?.loadListToAdapter(List)
        }
        return List
    }
}