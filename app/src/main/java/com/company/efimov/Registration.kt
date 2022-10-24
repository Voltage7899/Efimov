package com.company.efimov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.company.efimov.databinding.ActivityRegistrationBinding
import com.google.firebase.database.*

class Registration : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    private var database: DatabaseReference = FirebaseDatabase.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.regReg.setOnClickListener {


            try {
                if(!binding.regPhone.text.isEmpty()&&!binding.regPass.text.isEmpty()&&!binding.fio.text.isEmpty()&&!binding.adress.text.isEmpty()&&!binding.appartament.text.isEmpty()){
                    val user= userModel(binding.regPhone.text.toString(),binding.regPass.text.toString(),binding.fio.text.toString(),binding.adress.text.toString(),binding.appartament.text.toString(),"User")
                    database.child("User").addListenerForSingleValueEvent(object:
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (!snapshot.child(binding.regPhone.text.toString()).exists()) {
                                database.child("User").child(binding.regPhone.text.toString()).setValue(user)
                                Toast.makeText(this@Registration, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                                finish()

                            } else {
                                Toast.makeText(this@Registration, "Пользователь с такими данными уже есть", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
                else{
                    Toast.makeText(this@Registration, "Введите все данные в пустые поля", Toast.LENGTH_SHORT).show()

                }





            }
            catch (ex: Exception){
                Toast.makeText(this,"Телефон и код цифрами!!", Toast.LENGTH_SHORT).show()
            }



        }
    }
}