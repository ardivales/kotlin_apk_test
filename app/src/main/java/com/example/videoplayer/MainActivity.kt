package com.example.videoplayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.videoplayer.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Créez une instance de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Créez une instance de l'interface ApiService en utilisant Retrofit
        val apiService = retrofit.create(ApiService::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnLogin.setOnClickListener {

            val username = binding.edUsername.text.trim().toString()
            val password = binding.edPassword.text.trim().toString()


            if (username.isEmpty()) {
                binding.edUsername.error = "Username is required"
                binding.edUsername.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edPassword.error = "Password is required"
                binding.edPassword.requestFocus()
                return@setOnClickListener
            }

            // Créez une instance de LoginData en utilisant les données de l'interface utilisateur
            val loginData = LoginData(
                username,
                password
            )

            // Appelez la méthode register de l'API
            apiService.login(loginData).enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.isSuccessful) {
                        // Le registre a réussi
                        Toast.makeText(
                            this@MainActivity,
                            response.body()?.message,
                            Toast.LENGTH_LONG
                        ).show()
//                        val intent = Intent(applicationContext, CoverActivity::class.java)
                        val intent = Intent(applicationContext, MainVideo::class.java)
                        startActivity(intent)
                    } else {
                        // Le registre a échoué
                        Toast.makeText(
                            applicationContext,
                            "Failed to connect",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    // Une erreur s'est produite lors de la communication avec le serveur
                    Toast.makeText(
                        this@MainActivity,
                        "Error: " + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}