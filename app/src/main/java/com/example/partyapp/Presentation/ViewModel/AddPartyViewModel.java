package com.example.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Domain.Model.Person;
import com.example.partyapp.Presentation.Repository.Repository;

import java.time.LocalDateTime;

public class AddPartyViewModel extends ViewModel {
    public void AddParty(String name,
                         String maxPeopleCount,
                         String place,
                         String description,
                         LocalDateTime startTime,
                         LocalDateTime stopTime){
        int MaxPeopleCount = 0;
        if (!maxPeopleCount.isEmpty()) {
            MaxPeopleCount = Integer.parseInt(maxPeopleCount);
        }
        Party party = new Party(name, new Person("", ""), MaxPeopleCount);
        party.setPlace(place);
        party.setDescription(description);
        if (startTime != null) {
            party.setStartTime(startTime);
        }
        if (stopTime != null) {
            party.setStopTime(stopTime);
        }

        Repository.getRepository().addParty(party);
    }
}