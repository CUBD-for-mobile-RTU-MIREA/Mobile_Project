package com.example.partyapp.Presentation.Repository.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.partyapp.Presentation.Repository.Model.PartyDTO;
import com.example.partyapp.Presentation.Repository.Room.DAO.PartyDAO;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {PartyDTO.class}, version = 1, exportSchema = false)
public abstract class PartyRoomDatabase extends RoomDatabase {
    public abstract PartyDAO partyDao();

    private static volatile PartyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PartyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PartyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PartyRoomDatabase.class, "word_database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
