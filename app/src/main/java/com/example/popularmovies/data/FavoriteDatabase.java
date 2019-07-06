package com.example.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {


    private static FavoriteDatabase INSTANCE;
    private static final String DATABASE_NAME = "favorites";

    public static FavoriteDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            FavoriteDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();

}
