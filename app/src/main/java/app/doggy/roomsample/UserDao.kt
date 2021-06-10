package app.doggy.roomsample

import androidx.room.*

@Dao
interface UserDao {

    @Insert
    fun insert(user : User)

    @Insert
    fun insertAll(vararg users: User)

    @Update
    fun update(user : User)

    @Delete
    fun delete(user: User)

    @Query("delete from users")
    fun deleteAll()

    @Query("select * from users")
    fun getAll(): List<User>

    @Query("select * from users where id = :id")
    fun getUser(id: Int): User

    @Query("SELECT * FROM users WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
}