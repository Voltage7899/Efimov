package com.company.efimov

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.company.efimov.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    var List: ArrayList<CompanyModel> =ArrayList<CompanyModel>()
    var ListString:ArrayList<String> = ArrayList<String>()
    val TypesList = arrayOf("Свет","Электричество","Газ")
    lateinit var binding: ActivityMainBinding
    var spinnerAdapter: ArrayAdapter<String> ?= null
    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var currentCom:CompanyModel?=null
    var currentType:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()


        binding.spinnerCompanies.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                currentCom=List.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        val spinnerTypesAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,TypesList)
        binding.spinnerTypes.adapter=spinnerTypesAdapter
        binding.spinnerTypes.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                currentType=TypesList.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.admin -> {
                    val admin = Intent(this, RegAvtorization::class.java)
                    startActivity(admin)
                }
                R.id.companies->{
                    val admin = Intent(this, Companies::class.java)
                    startActivity(admin)
                }
                R.id.kvitancia->{
                    val admin = Intent(this, YourKvitancia::class.java)
                    startActivity(admin)
                }
            }

            true
        }

        binding.formKvitan.setOnClickListener {
            if (userModel.currentuser?.fio == null) {
                Toast.makeText(this, "Сперва вы должны войти как юзер", Toast.LENGTH_SHORT).show()
            } else {
                var price = currentCom?.tarif?.toInt()
                    ?.times(binding.pokazanieSchetchika.text.toString().toInt())
                val kvitan = KvitanciaModel(
                    binding.date.text.toString(),
                    binding.pokazanieSchetchika.text.toString(),
                    currentType,
                    currentCom?.name,
                    userModel.currentuser?.fio,
                    userModel.currentuser?.address,
                    userModel.currentuser?.appartament,
                    price.toString()
                )
                database.getReference("Kvitan").child(userModel.currentuser?.phone.toString())
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.child(binding.date.text.toString()).exists()) {
                                database.getReference("Kvitan")
                                    .child(userModel.currentuser?.phone.toString())
                                    .child(binding.date.text.toString()).setValue(kvitan)
                                Toast.makeText(this@MainActivity, "Квитанция сформирована", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                    })
            }


        }

    }
    fun getData() {


        val listComString = arrayListOf("")
        database.getReference("Company").get().addOnSuccessListener {
            listComString.clear()
            for (t in it.children) {

                val kino = t.getValue(CompanyModel::class.java)
                if (kino != null) {
                    List.add(kino)
                    listComString.add(kino.name.toString())
                }

            }
            spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,listComString)
            binding.spinnerCompanies.adapter=spinnerAdapter

        }

    }
}