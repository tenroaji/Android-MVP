package lagecong.com.mvp.data.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "artikels")
class ArtikelEntity(
        @PrimaryKey
        @ColumnInfo(name = "id") var mId : Int,
        @ColumnInfo(name = "data_artikel") var dataArtikel : String?,
        @ColumnInfo(name = "date_artikel", index = true) var dateArtikel : String
)