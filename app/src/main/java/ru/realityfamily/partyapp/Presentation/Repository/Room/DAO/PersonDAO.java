package ru.realityfamily.partyapp.Presentation.Repository.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.realityfamily.partyapp.Presentation.Repository.Model.PersonDTO;

@Dao
public interface PersonDAO {
    @Insert
    void addPerson(PersonDTO person);

    @Query("SELECT * FROM person WHERE email = :email")
    LiveData<PersonDTO> getPersonByEmail(String email);

    @Query("SELECT * FROM person WHERE email = :email AND password = :password")
    LiveData<PersonDTO> getPersonByEmailAndPassword(String email, String password);

    @Query("SELECT * FROM person")
    LiveData<List<PersonDTO>> getAllPeople();

    @Update
    void updatePersonInfo(PersonDTO person);
}
