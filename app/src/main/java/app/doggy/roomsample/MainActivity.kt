package app.doggy.roomsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.doggy.roomsample.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val USERLIST = "userList"
    }

    //ViewBindingを使ってみた
    private lateinit var binding: ActivityMainBinding

    //CoroutineScopeをMainに設定
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    //Roomを使用する準備
    val database by lazy { AppDatabase.getDatabase(this) }
    val userDao by lazy { database.userDao() }

    private val userList: MutableList<User> = mutableListOf()
    private val userAdapter by lazy { UserAdapter(baseContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RecyclerViewの設定
        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = userAdapter

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(baseContext, NewUserActivity::class.java)
            startActivity(intent)
        }

        applicationScope.launch {
            withContext(Dispatchers.IO) {
                userList.addAll(userDao.getAll())
                Log.d(USERLIST, "got userList")
                Log.d(USERLIST, userList.toString())

                if (userList.isEmpty()) {
                    userDao.insert(User(0, "Kaito", "Dogi", 22))
                    Log.d(USERLIST, "added dummy data.")
                } else {
                    Log.d(USERLIST, "userList is not empty.")
                }

            }

            userAdapter.addAll(userList)
            Log.d(USERLIST, "updated userAdapter")

        }

    }

}