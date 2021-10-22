package ru.realityfamily.partyapp.Domain.Operations;

import java.time.LocalDateTime;
import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;

public class PartyOperations {
    public static Party addParty(String name,
                                 Person creator,
                                 int maxPeopleCount,
                                 String place,
                                 String description,
                                 LocalDateTime startTime,
                                 LocalDateTime stopTime,
                                 List<String> images,
                                 boolean verified) {
        Party party = new Party();
        party.setName(name);
        if (creator != null) {
            party.setCreator(creator);
        } else {
            party.setCreator(new Person("", "неизвестный"));
        }
        party.setMaxPeopleCount(maxPeopleCount);
        party.setPlace(place);
        party.setDescription(description);
        party.setStartTime(startTime);
        party.setStopTime(stopTime);
        party.getPeopleList().add(party.getCreator());
        party.setImages(images);
        party.setVerified(verified);
        return party;
    }
}
