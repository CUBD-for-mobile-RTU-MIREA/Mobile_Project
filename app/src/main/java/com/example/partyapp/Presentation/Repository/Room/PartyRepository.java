package com.example.partyapp.Presentation.Repository.Room;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Presentation.Repository.Model.PartyDTO;
import com.example.partyapp.Presentation.Repository.RepositoryTasks;
import com.example.partyapp.Presentation.Repository.Room.DAO.PartyDAO;

import java.util.List;

public class PartyRepository implements RepositoryTasks {
    private PartyDAO mPartyDao;
    private LiveData<List<PartyDTO>> mAllParties = new MutableLiveData<>();

    public PartyRepository(Application application) {
        PartyRoomDatabase db = PartyRoomDatabase.getDatabase(application);
        mPartyDao = db.partyDao();
        mAllParties = mPartyDao.getAllParties();
    }

    public LiveData<List<PartyDTO>> getAllParties() {
        return mAllParties;
    }

    @Override
    public <T extends Party> void addParty(T party) {
        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPartyDao.addParty(((PartyDTO) party));
        });
    }

    @Override
    public <T extends Party> void deleteParty(T party) {
        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPartyDao.deleteParty(((PartyDTO) party));
        });
    }
}
