package lagecong.com.mvp.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "artikel")
class Artikel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "image")
    val image: String,
    @ColumnInfo(name = "published")
    val published: String,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "content")
    val content: String
)
