package com.company.efimov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.company.efimov.databinding.ActivityCompaniesBinding
import com.company.efimov.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.R

class Companies : AppCompatActivity() {
    var ListAdapter:AdapterCompany?=null
    lateinit var binding: ActivityCompaniesBinding
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompaniesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomMenuCompanies.selectedItemId = com.company.efimov.R.id.companies
        binding.bottomMenuCompanies.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                com.company.efimov.R.id.indication -> {
                    val admin = Intent(this, MainActivity::class.java)
                    startActivity(admin)
                }
                com.company.efimov.R.id.admin->{
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
                com.company.efimov.R.id.kvitancia->{
                    val admin = Intent(this, YourKvitancia::class.java)
                    startActivity(admin)
                }

            }

            true
        }
        binding.recCompanies.layoutManager= LinearLayoutManager(this)
        ListAdapter = AdapterCompany()
        binding.recCompanies.adapter=ListAdapter
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
}