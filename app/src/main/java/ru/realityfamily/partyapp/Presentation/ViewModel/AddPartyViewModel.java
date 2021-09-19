package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.Domain.Operations.PartyOperations;

public class AddPartyViewModel extends ViewModel {
    public void AddParty(String name,
                         String maxPeopleCount,
                         String place,
                         String description,
                         LocalDateTime startTime,
                         LocalDateTime stopTime,
                         List<String> images){
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
                stopTime,
                images.stream().filter(Objects::nonNull).collect(Collectors.toList())
                );

        ServiceLocator.getInstance().getRepository().addParty(party);
    }
}