package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.Domain.Operations.PartyOperations;
import ru.realityfamily.partyapp.Presentation.Repository.Repository;

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

        Party party = PartyOperations.addParty(
                name,
                new Person("Леонид", "Шешуков"),
                MaxPeopleCount,
                place,
                description,
                startTime,
                stopTime
                );

        Repository.getRepository().addParty(party);
    }
}