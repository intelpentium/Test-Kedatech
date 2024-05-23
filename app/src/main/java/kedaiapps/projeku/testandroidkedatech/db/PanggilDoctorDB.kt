package kedaiapps.projeku.testandroidkedatech.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kedaiapps.projeku.testandroidkedatech.db.table.FavoriteTable

@Database(entities = [FavoriteTable::class], version = 1)
abstract class KedatechDB : RoomDatabase(){

    companion object {
        private var INSTANCE: KedatechDB? = null

        fun getDatabase(context: Context): KedatechDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, KedatechDB::class.java, "KedatechDB")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as KedatechDB
        }
    }

    abstract fun daoFavorite() : daoFavorite
}