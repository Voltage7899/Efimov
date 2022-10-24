package com.company.efimov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.efimov.databinding.ActivityMainBinding
import com.company.efimov.databinding.ActivityWorkZoneBinding
import com.google.firebase.database.FirebaseDatabase

class WorkZone : AppCompatActivity() {
    lateinit var binding: ActivityWorkZoneBinding
    var ListAdapter:AdapterCompany?=null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkZoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.add.setOnClickListener {
            startActivity(Intent(this,AddCom::class.java))
        }

        binding.recCompaniesAdmin.layoutManager= LinearLayoutManager(this)
        ListAdapter = AdapterCompany()
        binding.recCompaniesAdmin.adapter=ListAdapter
        ListAdapter?.loadListToAdapter(getData())
    }
    fun getData():ArrayList<CompanyModel> {


        val List = ArrayList<CompanyModel>()
        database.getReference("Company").get().addOnSuccessListener {
            for (t in it.children) {

                val kino = t.getValue(CompanyModel::class.java)
                if (kino != null) {
                    List.add(kino)

                }
                ListAdapter?.loadListToAdapter(List)
            }

        }
        return List
    }

    override fun onStart() {
        super.onStart()
        ListAdapter?.loadListToAdapter(getData())
    }
}