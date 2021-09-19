package ru.realityfamily.partyapp.Presentation.Repository.Room;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Presentation.Repository.Model.PartyDTO;
import ru.realityfamily.partyapp.Presentation.Repository.RepositoryTasks;
import ru.realityfamily.partyapp.Presentation.Repository.Room.DAO.PartyDAO;

public class PartyRepository implements RepositoryTasks {
    private PartyDAO mPartyDao;
    private LiveData<List<PartyDTO>> mAllParties;

    public PartyRepository(Application application) {
        PartyRoomDatabase db = PartyRoomDatabase.getDatabase(application);
        mPartyDao = db.partyDao();
        mAllParties = mPartyDao.getAllParties();
    }

    public LiveData<List<PartyDTO>> getAllParties() {
        return mAllParties;
    }

    @Override
    public void addParty(Party party) {
        PartyDTO dto = PartyDTO.convertFromParty(party);

        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPartyDao.addParty(dto);
        });
    }

    @Override
    public void deleteParty(Party party) {
        PartyDTO dto = PartyDTO.convertFromParty(party);

        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPartyDao.deleteParty(dto);
        });
    }

    @Override
    public MutableLiveData<PartyDTO> findParty(String id, LifecycleOwner owner) {
        MutableLiveData<PartyDTO> specificParty = new MutableLiveData<>();

        mAllParties.observe(owner, (List<PartyDTO> parties) -> {
            specificParty.setValue(parties.stream()
                    .filter(partyDTO -> id.equals(partyDTO.getId()))
                    .findAny()
                    .orElse(null)
            );
        });
        return specificParty;
    }
}
