package com.project.music.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {AllSongsEntities.class}, version = 1)
abstract class AllSongDatabase extends RoomDatabase {
    abstract public AllSongsDao allSongsDao();
}

