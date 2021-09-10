package com.example.partyapp.Presentation.Repository.Room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.partyapp.Presentation.Repository.Model.PartyDTO;

import java.util.List;

@Dao
public interface PartyDAO {
    @Insert
    void addParty(PartyDTO party);

    @Delete
    void deleteParty(PartyDTO party);

    @Query("SELECT * FROM party")
    LiveData<List<PartyDTO>> getAllParties();
}
