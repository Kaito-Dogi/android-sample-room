package app.doggy.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import app.doggy.roomsample.databinding.ActivityNewUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding

    //CoroutineScopeをMainに設定
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val database = MainActivity().database
    private val userDao by lazy { database.userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveButton.setOnClickListener {
            applicationScope.launch(Dispatchers.IO) {
                userDao.insert(User(
                    0,
                    binding.firstNameEditText.text.toString(),
                    binding.lastNameEditText.text.toString(),
                    binding.ageEditText.text.toString().toInt()))
                finish()
            }
        }
    }
}