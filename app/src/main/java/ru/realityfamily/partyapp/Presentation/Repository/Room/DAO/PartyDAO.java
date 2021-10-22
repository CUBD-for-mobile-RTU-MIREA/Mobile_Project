package ru.realityfamily.partyapp.Presentation.Repository.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.realityfamily.partyapp.Presentation.Repository.Model.PartyDTO;

@Dao
public interface PartyDAO {
    @Insert
    void addParty(PartyDTO party);

    @Update
    void updateParty(PartyDTO party);

    @Delete
    void deleteParty(PartyDTO party);

    @Query("SELECT * FROM party")
    LiveData<List<PartyDTO>> getAllParties();

    @Query("SELECT * FROM party WHERE verified = 1")
    LiveData<List<PartyDTO>> getVerifiedParties();

    @Query("SELECT * FROM party WHERE verified = 0")
    LiveData<List<PartyDTO>> getNotVerifiedParties();
}
