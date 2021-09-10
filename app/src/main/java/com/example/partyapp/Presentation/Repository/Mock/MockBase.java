package com.example.partyapp.Presentation.Repository.Mock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Domain.Model.Person;
import com.example.partyapp.Presentation.Repository.RepositoryTasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockBase implements RepositoryTasks {
    MutableLiveData<List<Party>> data;
    List<Party> list;

    public MutableLiveData<List<Party>> getAllParties() {
        return data;
    }

    public MockBase() {
        list = new ArrayList<>();

        Party party1 = new Party("Туса 1",
                new Person("Леонид", "Шешуков"),
                -1);
        party1.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party1.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party1.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party1);

        Party party2 = new Party("Туса 2",
                new Person("Леонид", "Шешуков"),
                -1);
        party2.setStartTime(LocalDateTime.of(2021, 9, 15, 18, 0));
        party2.setStopTime(LocalDateTime.of(2021, 9, 15, 19, 0));
        party2.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party2);

        Party party3 = new Party("Туса 3",
                new Person("Леонид", "Шешуков"),
                -1);
        party3.setStartTime(LocalDateTime.of(2021, 10, 3, 9, 0));
        party3.setStopTime(LocalDateTime.of(2021, 11, 21, 21, 0));
        party3.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party3);

        Party party4 = new Party("Туса 4",
                new Person("Леонид", "Шешуков"),
                20);
        party4.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party4.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party4.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party4);

        Party party5 = new Party("Туса 5",
                new Person("Леонид", "Шешуков"),
                15);
        party5.setStartTime(LocalDateTime.of(2021, 9, 20, 10, 30));
        party5.setStopTime(LocalDateTime.of(2021, 9, 21, 11, 0));
        party5.setPlace("г. Москва, ул. 9-Вишневая, д. 30");
        list.add(party5);

        data = new MutableLiveData<>(list);
    }

    public void addParty(Party party) {
        list.add(party);

        data.setValue(list);
    }

    public void deleteParty(int position) {
        list.remove(position);

        data.setValue(list);
    }
}
