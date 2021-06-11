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

            //MainActivityから渡されたidをもとに，Userのデータを取得したい．

//            applicationScope.launch {
//                withContext(Dispatchers.IO) {
//                    val user = userDao.getUser(userId)
//                }
//                binding.firstNameEditText.setText(user.firstName)
//                binding.lastNameEditText.setText(user.lastName)
//                binding.ageEditText.setText(user.age.toString())
//            }

            binding.saveButton.text = "UPDATE"
        }

        binding.saveButton.setOnClickListener {
            applicationScope.launch {
                withContext(Dispatchers.IO) {
                    if (binding.firstNameEditText.text.toString() != "" &&
                        binding.lastNameEditText.text.toString() != "" &&
                        binding.ageEditText.text.toString() != ""
                    ) {
                        userDao.insert(User(
                            userId,
                            binding.firstNameEditText.text.toString(),
                            binding.lastNameEditText.text.toString(),
                            binding.ageEditText.text.toString().toInt()))
                    }
                }

                //データが保存されなかった時にToastを表示したい．

                finish()
            }
        }
    }
}