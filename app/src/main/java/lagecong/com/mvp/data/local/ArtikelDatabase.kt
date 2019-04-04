package lagecong.com.mvp.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = [ArtikelEntity::class,ArticleDetailEntity::class], version = 1)
abstract class ArtikelDatabase : RoomDatabase() {

    abstract fun artikelDAO(): ArtikelDAO
    abstract fun getArticleDetailDao(): ArticleDetailDao

    companion object {

        private var INSTANCE: ArtikelDatabase? = null

        private val slock = Any()

        fun getInstance(context: Context): ArtikelDatabase {

//            @VisibleForTesting
//            val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//                override fun migrate(database: SupportSQLiteDatabase) {
//                    database.execSQL(
//                        "CREATE TABLE djki_kemenkumham (id TEXT NOT NULL,"
//                                + "data TEXT,"
//                                + "PRIMARY KEY(id))"
//                    )
//                }
//            }


            synchronized(slock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, ArtikelDatabase::class.java, "artikel.djki")
//                            .addMigrations(MIGRATION_1_2)
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}
