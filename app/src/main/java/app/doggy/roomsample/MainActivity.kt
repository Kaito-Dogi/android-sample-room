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
        private const val USER_LIST = "userList"
        const val USER_ID = "userId"
    }

    //ViewBindingを使ってみた
    private lateinit var binding: ActivityMainBinding

    //CoroutineScopeをMainに設定
    private val applicationScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    //Roomを使用する準備
    val database by lazy { AppDatabase.getDatabase(this) }
    private val userDao by lazy { database.userDao() }

    private val userAdapter by lazy {
        UserAdapter(
            baseContext,
            object : UserAdapter.OnItemClickListener {
                override fun onItemClick(item: User) {

                    val id = item.id
                    Log.d(USER_ID, id.toString())

                    val intent = Intent(baseContext, SaveActivity::class.java)
                    intent.putExtra(USER_ID, id)
                    startActivity(intent)

                }
            },
//            object : UserAdapter.OnItemLongClickListener {
//                override fun onItemLongClick(item: User) {
//
//                    val id = item.id
//                    Log.d(USER_ID, id.toString())
//
//                    applicationScope.launch(Dispatchers.IO) {
//                        userDao.delete(userDao.getUser(item.id))
//                    }
//
//                }
//            }
        )
    }

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
            val intent = Intent(baseContext, SaveActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()

        val userList: MutableList<User> = mutableListOf()

        applicationScope.launch {
            withContext(Dispatchers.IO) {
                userList.addAll(userDao.getAll())
                Log.d(USER_LIST, "got userList")
                Log.d(USER_LIST, userList.toString())

                //ダミーデータの生成
                if (userList.isEmpty()) {
                    userDao.insert(User(0, "Kaito", "Dogi", 22))
                    userList.addAll(userDao.getAll())
                    Log.d(USER_LIST, "added dummy data.")
                } else {
                    Log.d(USER_LIST, "userList is not empty.")
                }

            }

            userAdapter.addAll(userList)
            Log.d(USER_LIST, "updated userAdapter")

        }

    }

}