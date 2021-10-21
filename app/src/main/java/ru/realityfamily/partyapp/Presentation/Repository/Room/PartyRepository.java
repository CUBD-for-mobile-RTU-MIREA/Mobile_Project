package ru.realityfamily.partyapp.Presentation.Repository.Room;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.Presentation.Repository.Model.PartyDTO;
import ru.realityfamily.partyapp.Presentation.Repository.Model.PersonDTO;
import ru.realityfamily.partyapp.Presentation.Repository.RepositoryTasks;
import ru.realityfamily.partyapp.Presentation.Repository.Room.DAO.PartyDAO;
import ru.realityfamily.partyapp.Presentation.Repository.Room.DAO.PersonDAO;

public class PartyRepository implements RepositoryTasks {
    private PartyDAO mPartyDao;
    private PersonDAO mPersonDao;
    private LiveData<List<PartyDTO>> mAllParties;

    public PartyRepository(Application application) {
        PartyRoomDatabase db = PartyRoomDatabase.getDatabase(application);
        mPartyDao = db.partyDao();
        mPersonDao = db.personDAO();
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
    public void addPerson(Person person) {
        PersonDTO dto = PersonDTO.convertFromPerson(person);

        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.addPerson(dto);
        });
    }

    @Override
    public void updatePerson(Person person) {
        PersonDTO dto = PersonDTO.convertFromPerson(person);

        PartyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mPersonDao.updatePersonInfo(dto);
        });
    }

    @Override
    public LiveData<PersonDTO> findPerson(String email, LifecycleOwner owner) {
        MutableLiveData<PersonDTO> answer = new MutableLiveData<>();

        mPersonDao.getPersonByEmail(email).observe(owner, answer::setValue);

        return answer;
    }

    @Override
    public LiveData<PersonDTO> findPerson(String email, String password, LifecycleOwner owner) {
        MutableLiveData<PersonDTO> answer = new MutableLiveData<>();

        mPersonDao.getPersonByEmailAndPassword(email, password).observe(owner, answer::setValue);

        return answer;
    }

    @Override
    public LiveData<PartyDTO> findParty(String id, LifecycleOwner owner) {
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
