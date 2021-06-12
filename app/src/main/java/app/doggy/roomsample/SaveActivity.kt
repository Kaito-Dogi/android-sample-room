package app.doggy.roomsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.doggy.roomsample.databinding.ActivitySaveBinding
import kotlinx.coroutines.*

class SaveActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveBinding

    //CoroutineScopeをMainに設定
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    //RoomのインスタンスはMainActivityから取得
    private val database = MainActivity().database
    private val userDao by lazy { database.userDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra(MainActivity.USER_ID, 0)
        Log.d(MainActivity.USER_ID, userId.toString())

        if (userId != 0) {

            applicationScope.launch {

                //MainActivityから渡されたidをもとに，Userのデータを取得
                val user = withContext(Dispatchers.IO) {
                    userDao.getUser(userId)
                }

                binding.firstNameEditText.setText(user.firstName)
                binding.lastNameEditText.setText(user.lastName)
                binding.ageEditText.setText(user.age.toString())

            }

            binding.saveButton.text = "UPDATE"

        }

        binding.saveButton.setOnClickListener {
            applicationScope.launch(Dispatchers.IO) {

                if (binding.firstNameEditText.text.toString() != "" &&
                    binding.lastNameEditText.text.toString() != "" &&
                    binding.ageEditText.text.toString() != ""
                ) {
                    val user = User(
                        userId,
                        binding.firstNameEditText.text.toString(),
                        binding.lastNameEditText.text.toString(),
                        binding.ageEditText.text.toString().toInt())

                    when(userId) {
                        0 -> userDao.insert(user)
                        else -> userDao.update(user)
                    }

                    finish()

                } else {
                    //空欄がある時にToastを表示
                    launch(Dispatchers.Main) {
                        Toast.makeText(baseContext, "Input data!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}