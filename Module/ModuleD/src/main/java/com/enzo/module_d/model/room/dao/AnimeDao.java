package com.enzo.module_d.model.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.enzo.module_d.model.room.entity.Anime;

import java.util.List;

@Dao
public interface AnimeDao {

    @Insert
    void insertOneAnime(Anime anime); //插入一条动漫信息

    @Delete
    void deleteAnime(Anime anime);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateUsers(Anime... animes); //更新动漫信息，当有冲突时则进行替代

    @Query("SELECT * FROM anime WHERE name = :name")
    Anime loadAnimeByName(String name); //根据名字加载动漫

    @Query("SELECT * FROM  anime")
    List<Anime> getAllAnime(); //加载所有动漫数据

    @Insert
    void insertMultiAnimes(Anime... animes); //插入多条动漫信息
}
