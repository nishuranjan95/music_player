package com.project.music.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
public interface AllSongsDao {

    @Insert
    void insert(AllSongsEntities allSongsEntities);

    @Delete
    void deleteSong(AllSongsEntities allSongsEntities);
/*
    @Query("SELECT * FROM songsTable")
    ArrayList<AllSongsEntities> getAllSongs();

    @Query("SELECT * FROM songsTable WHERE id= :songId")
    AllSongsEntities getAllSongsById(String songId);

 */
}
