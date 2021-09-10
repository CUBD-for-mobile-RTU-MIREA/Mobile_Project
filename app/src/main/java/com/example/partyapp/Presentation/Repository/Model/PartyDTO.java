package com.example.partyapp.Presentation.Repository.Model;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Domain.Model.Person;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartyDTO {
    private String name;
    private String description;
    private String place;
    private String creator;
    private String startTime;
    private String stopTime;
    private int peopleCount;
    private int maxPeopleCount;
    private List<String> peopleList = new ArrayList<>();

    PartyDTO(Party party) {
        this.name = party.getName();
        this.description = party.getDescription();
        this.place = party.getPlace();
        this.creator = new Gson().toJson(party.getCreator());
        this.startTime = party.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.stopTime = party.getStopTime().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        this.maxPeopleCount = party.getMaxPeopleCount();

        party.getPeopleList().forEach((Person person) -> this.peopleList.add(new Gson().toJson(person)));
    }

    Party getParty() {
        Party out = new Party(this.name,
                new Gson().fromJson(this.creator, Person.class),
                this.maxPeopleCount);

        out.setPlace(this.place);
        out.setDescription(this.description);
        out.setStartTime(LocalDateTime.parse(this.startTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        out.setStopTime(LocalDateTime.parse(this.stopTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        out.setPeopleList(this.peopleList.stream()
                .map((String person) -> new Gson().fromJson(person, Person.class))
                .collect(Collectors.toList())
        );

        return out;
    }
}
