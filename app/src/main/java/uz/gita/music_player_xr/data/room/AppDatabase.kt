package uz.gita.music_player_xr.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.music_player_xr.data.model.MusicData
import uz.gita.music_player_xr.data.room.dao.MusicDao
import uz.gita.music_player_xr.utils.MusicListTypeConverter

// Created by Jamshid Isoqov an 10/7/2022
@Database(entities = [MusicData::class], version = 8)
@TypeConverters(MusicListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao
}