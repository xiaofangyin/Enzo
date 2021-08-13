package com.enzo.module_d.model.room.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.enzo.module_d.model.room.dao.AnimeDao;
import com.enzo.module_d.model.room.entity.Anime;

@Database(entities = {Anime.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract AnimeDao getAnimDao();
}
