package com.project.music.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songsTable")
public class AllSongsEntities {

    @PrimaryKey int id;
    @ColumnInfo(name = "songTrack") String songTrack;
    @ColumnInfo(name = "artistTrack") String artistTrack;

}
