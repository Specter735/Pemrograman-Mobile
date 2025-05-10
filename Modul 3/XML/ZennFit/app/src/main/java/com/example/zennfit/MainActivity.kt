package com.example.zennfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zennfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    data class AlatGym(
        val nama: String,
        val descSingkat: String,
        val deskripsi: String,
        val gambarResId: Int,
        val gifResId: Int
    )

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Tampilkan HomeFragment sebagai default
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }
}
