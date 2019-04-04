package lagecong.com.mvp.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "article_detail")
class ArticleDetailEntity(
        @PrimaryKey
        @ColumnInfo(name = "id") var id : String,
        @ColumnInfo(name = "data_menu") var data_menu : String?,
        @ColumnInfo(name = "index") var index : Int?
)