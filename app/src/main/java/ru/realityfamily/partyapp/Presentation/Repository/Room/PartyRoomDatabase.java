package ru.realityfamily.partyapp.Presentation.Repository.Room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.Presentation.Repository.Model.PartyDTO;
import ru.realityfamily.partyapp.Presentation.Repository.Model.PersonDTO;
import ru.realityfamily.partyapp.Presentation.Repository.Room.DAO.PartyDAO;
import ru.realityfamily.partyapp.Presentation.Repository.Room.DAO.PersonDAO;

@Database(entities = {PartyDTO.class, PersonDTO.class}, version = 1, exportSchema = false)
public abstract class PartyRoomDatabase extends RoomDatabase {
    public abstract PartyDAO partyDao();
    public abstract PersonDAO personDAO();

    private static volatile PartyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PartyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PartyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PartyRoomDatabase.class, "partyApp_database")
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);

                                    databaseWriteExecutor.execute( () -> {
                                                PersonDTO admin = new PersonDTO();
                                                admin.setEmail("admin@mirea.ru");
                                                admin.setPassword("admin");
                                                admin.setRole(Person.Role.Admin);

                                                getDatabase(context).personDAO().addPerson(admin);

                                                PersonDTO moder = new PersonDTO();
                                                moder.setEmail("moder@mirea.ru");
                                                moder.setPassword("moder");
                                                moder.setRole(Person.Role.Moder);

                                                getDatabase(context).personDAO().addPerson(moder);
                                            }
                                    );
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
