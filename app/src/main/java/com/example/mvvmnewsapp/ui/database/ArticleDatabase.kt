package com.example.mvvmnewsapp.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmnewsapp.ui.model.Article

@Database(
    entities = [Article::class], //list of entites or tables in our database
    version = 1, // used to update our database later on
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao



    //creates an instance of our database
    companion object {
        @Volatile
        //create an single instance of our database
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        //if the instance is null/doesnt exist then we create a new database within a synchronized
        //block
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            //everything inside this block of code cant be accessed by another thread because of
            //Lock
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }

}