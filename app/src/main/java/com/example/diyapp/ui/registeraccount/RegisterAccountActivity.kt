package com.example.diyapp.ui.registeraccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.diyapp.databinding.ActivityRegisterAccountBinding

class RegisterAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}