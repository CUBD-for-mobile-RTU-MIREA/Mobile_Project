package com.example.partyapp.Presentation.Repository.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Domain.Model.Person;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(tableName = "party")
public class PartyDTO extends Party {
    @PrimaryKey
    @NotNull
    @ColumnInfo
    public int id;
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String startTime;
    @ColumnInfo
    public String stopTime;
    @ColumnInfo
    public String peopleList;

    @Override
    public Person getCreator() {
        return new Gson().fromJson(this.creator, Person.class);
    }
    @Override
    public void setCreator(Person creator) {
        super.setCreator(creator);
        this.creator = new Gson().toJson(creator);
    }
    @Override
    public LocalDateTime getStartTime() {
        if (this.startTime != null) {
            return LocalDateTime.parse(this.startTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            return null;
        }
    }
    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        this.startTime = startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    @Override
    public LocalDateTime getStopTime() {
        if (this.stopTime != null) {
            return LocalDateTime.parse(this.stopTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            return null;
        }
    }
    @Override
    public void setStopTime(LocalDateTime stopTime) {
        super.setStopTime(stopTime);
        this.stopTime = stopTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    @Override
    public List<Person> getPeopleList() {
        return new Gson().fromJson(this.peopleList, List.class);
    }
    @Override
    public void setPeopleList(List<Person> peopleList) {
        super.setPeopleList(peopleList);
        this.peopleList = new Gson().toJson(peopleList);
    }
}
