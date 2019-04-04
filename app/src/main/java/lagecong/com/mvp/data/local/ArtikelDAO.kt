package lagecong.com.mvp.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface ArtikelDAO {

    @get:Query("SELECT * FROM artikels ORDER BY id DESC")
    val dataArtikel: List<ArtikelEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArtikel(artikel: ArtikelEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertsArtikels(artikels: List<ArtikelEntity>)


    @Query("DELETE FROM artikels WHERE id = :idArtikel")
    fun deleteArtikelById(idArtikel: Int?)

    @Query("DELETE FROM artikels")
    fun deleteAllArtikel()

}
