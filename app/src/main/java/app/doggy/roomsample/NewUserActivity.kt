package app.doggy.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.doggy.roomsample.databinding.ActivityNewUserBinding

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding

    private val database = MainActivity().database
    private val userDao = MainActivity().userDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}