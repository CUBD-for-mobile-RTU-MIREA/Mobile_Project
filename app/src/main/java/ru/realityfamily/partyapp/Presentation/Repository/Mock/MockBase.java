package ru.realityfamily.partyapp.Presentation.Repository.Mock;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.Presentation.Repository.RepositoryTasks;

public class MockBase implements RepositoryTasks {
    MutableLiveData<List<Party>> data;
    List<Party> list;

    public MutableLiveData<List<Party>> getAllParties() {
        return data;
    }

    public MockBase() {
        list = new ArrayList<>();

        Party party1 = new Party();
        party1.setName("Туса 1");
        party1.setCreator(new Person("Леонид", "Шешуков"));
        party1.setMaxPeopleCount(-1);
        party1.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party1.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party1.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party1);

        Party party2 = new Party();
        party2.setName("Туса 2");
        party2.setCreator(new Person("Леонид", "Шешуков"));
        party2.setMaxPeopleCount(-1);
        party2.setStartTime(LocalDateTime.of(2021, 9, 15, 18, 0));
        party2.setStopTime(LocalDateTime.of(2021, 9, 15, 19, 0));
        party2.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party2);

        Party party3 = new Party();
        party3.setName("Туса 3");
        party3.setCreator(new Person("Леонид", "Шешуков"));
        party3.setMaxPeopleCount(-1);
        party3.setStartTime(LocalDateTime.of(2021, 10, 3, 9, 0));
        party3.setStopTime(LocalDateTime.of(2021, 11, 21, 21, 0));
        party3.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party3);

        Party party4 = new Party();
        party4.setName("Туса 4");
        party4.setCreator(new Person("Леонид", "Шешуков"));
        party4.setMaxPeopleCount(20);
        party4.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party4.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party4.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party4);

        Party party5 = new Party();
        party5.setName("Туса 5");
        party5.setCreator(new Person("Леонид", "Шешуков"));
        party5.setMaxPeopleCount(15);
        party5.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party5.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party5.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party5);

        data = new MutableLiveData<>(list);
    }

    @Override
    public <T extends Party> LiveData<List<T>> getVerifiedParties() {
        return null;
    }

    @Override
    public <T extends Party> LiveData<List<T>> getNotVerifiedParties() {
        return null;
    }

    public void addParty(Party party) {
        list.add(party);

        data.setValue(list);
    }

    @Override
    public void deleteParty(Party party) {
        list.remove(party);

        data.setValue(list);
    }

    @Override
    public <T extends Party> LiveData<T> findVerifiedParty(String id, LifecycleOwner owner) {
        return null;
    }

    @Override
    public void updateParty(Party party) {

    }

    @Override
    public <T extends Person> LiveData<T> findPerson(String email, LifecycleOwner owner) {
        return null;
    }

    @Override
    public <T extends Person> LiveData<T> findPerson(String email, String password, LifecycleOwner owner) {
        return null;
    }

    @Override
    public void addPerson(Person person) {

    }

    @Override
    public void updatePerson(Person person) {

    }

    @Override
    public MutableLiveData<Party> findParty(String id, LifecycleOwner owner) {
        MutableLiveData<Party> specificParty = new MutableLiveData<>();

        data.observe(owner, (List<Party> parties) -> {
            specificParty.setValue(parties.stream()
                    .filter(party -> id.equals(party.getId()))
                    .findAny()
                    .orElse(null)
            );
        });

        return specificParty;
    }
}
