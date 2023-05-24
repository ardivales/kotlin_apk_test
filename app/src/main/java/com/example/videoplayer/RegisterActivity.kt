package com.example.videoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.videoplayer.databinding.ActivityMainBinding
import com.example.videoplayer.databinding.ActivityRegsiterBinding
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.util.Log

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegsiterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Créez une instance de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Créez une instance de l'interface ApiService en utilisant Retrofit
        val apiService = retrofit.create(ApiService::class.java)


        setContentView(R.layout.activity_regsiter)
        binding = ActivityRegsiterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnRegister.setOnClickListener {

            val email = binding.editEmail.text.trim()
            val username = binding.editUsername.text.trim()
            val password = binding.editPassword.text.trim()

            if (username.isEmpty()) {
                binding.editUsername.error = "Username is required"
                binding.editUsername.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.editEmail.error = "Email is required"
                binding.editEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.editPassword.error = "Password is required"
                binding.editPassword.requestFocus()
                return@setOnClickListener
            }

            // Créez une instance de RegistrationData en utilisant les données de l'interface utilisateur
            val registrationData = RegistrationData(
                email = binding.editEmail.text.trim().toString(),
                username = binding.editUsername.text.trim().toString(),
                password = binding.editPassword.text.trim().toString(),
            )
            // Appelez la méthode register de l'API
            apiService.register(registrationData).enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {

                    if (response.isSuccessful) {
                        // Le registre a réussi
                        Toast.makeText(
                            this@RegisterActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // Le registre a échoué
                        Toast.makeText(
                            applicationContext,
                            "Failed to register",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    // Une erreur s'est produite lors de la communication avec le serveur
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error: " + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

        binding.tvLogin.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}