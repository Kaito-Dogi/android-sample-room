package app.doggy.roomsample

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    // 複数のインスタンスが同時にデータベースにアクセスすることを防ぐために、シングルトンとする。
    companion object {

        /**
         * @Volatile
         * マルチスレッド処理では、処理の効率化のために、同じフィールドの値を、それぞれのスレッドが個別にキャッシュすることがある。
         * そのため、同じフィールドにも関わらず、スレッドによって参照値が異なる危険性がある。
         * よって、Volatile修飾子を用いて、フィールドの値がキャッシュされることを抑制し、値の不整合を回避する。
         */
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // INSTANCEがnullでない時、INSTANCEを返す。
        // INSTANCEがnullの時、AppDatabaseをインスタンス化してINSTANCEに代入する。
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sample_room_database"
                ).build()
                INSTANCE = instance

                // instanceを返す。
                instance
            }
        }
    }
}
