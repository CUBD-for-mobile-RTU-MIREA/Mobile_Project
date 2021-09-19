package ru.realityfamily.partyapp.Presentation.Repository.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ru.realityfamily.partyapp.Presentation.Repository.Model.PartyDTO;

@Dao
public interface PartyDAO {
    @Insert
    void addParty(PartyDTO party);

    @Delete
    void deleteParty(PartyDTO party);

    @Query("SELECT * FROM party")
    LiveData<List<PartyDTO>> getAllParties();
}
