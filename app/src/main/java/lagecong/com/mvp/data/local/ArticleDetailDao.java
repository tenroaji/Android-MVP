package lagecong.com.mvp.data.local;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface ArticleDetailDao {
    @Query("SELECT * FROM article_detail WHERE id = :id")
    ArticleDetailEntity getArticleDetailById(String id);

    @Query("SELECT * FROM article_detail")
    List<ArticleDetailEntity> getArticleDetail();

    @Query("DELETE FROM article_detail")
    void deleteAll();

   @Query("DELETE FROM article_detail WHERE `index` = :index")
    void deleteAll(Integer index);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void create(ArticleDetailEntity article_detail);

    @Update
    void update(ArticleDetailEntity article_detail);

    @Delete
    void delete(ArticleDetailEntity article_detail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ArticleDetailEntity> mListarticle_detail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEach(ArticleDetailEntity mListarticle_detail);
}
