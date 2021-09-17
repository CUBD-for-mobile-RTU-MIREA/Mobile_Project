package ru.realityfamily.partyapp.Presentation.Repository.Model;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;

@Entity(tableName = "party", primaryKeys = {"id"})
public class PartyDTO extends Party {
    @ColumnInfo
    public String creator;
    @ColumnInfo
    public String startTime;
    @ColumnInfo
    public String stopTime;
    @ColumnInfo
    public String peopleList;
    @ColumnInfo
    public String images;

    @Override
    public Person getCreator() {
        if (super.getCreator() != null) {
            return super.getCreator();
        } else {
            return new Gson().fromJson(this.creator, Person.class);
        }
    }
    @Override
    public void setCreator(Person creator) {
        super.setCreator(creator);
        this.creator = new Gson().toJson(creator);
    }
    @Override
    public LocalDateTime getStartTime() {
        if (super.getStartTime() != null) {
            return super.getStartTime();
        } else {
            if (this.startTime != null) {
                return LocalDateTime.parse(this.startTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            } else {
                return null;
            }
        }
    }
    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        if (startTime != null) {
            this.startTime = startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            this.startTime = null;
        }
    }
    @Override
    public LocalDateTime getStopTime() {
        if (super.getStopTime() != null) {
            return super.getStopTime();
        } else {
            if (this.stopTime != null) {
                return LocalDateTime.parse(this.stopTime, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
            } else {
                return null;
            }
        }
    }
    @Override
    public void setStopTime(LocalDateTime stopTime) {
        super.setStopTime(stopTime);
        if (stopTime != null) {
            this.stopTime = stopTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            this.stopTime = null;
        }
    }
    @Override
    public List<Person> getPeopleList() {
        if (super.getPeopleList() != null) {
            return super.getPeopleList();
        } else {
            return new Gson().fromJson(this.peopleList, List.class);
        }
    }
    @Override
    public void setPeopleList(List<Person> peopleList) {
        super.setPeopleList(peopleList);
        this.peopleList = new Gson().toJson(peopleList);
    }

    @Override
    public List<Bitmap> getImages() {
        return super.getImages();
    }

    @Override
    public void setImages(List<Bitmap> images) {
        super.setImages(images);
    }

    public static PartyDTO convertFromParty(Party party) {
        PartyDTO dto = new PartyDTO();

        dto.setId(party.getId());
        dto.setName(party.getName());
        dto.setCreator(party.getCreator());
        dto.setPlace(party.getPlace());
        dto.setDescription(party.getDescription());
        dto.setMaxPeopleCount(party.getMaxPeopleCount());
        dto.setStartTime(party.getStartTime());
        dto.setStopTime(party.getStopTime());
        dto.setPeopleList(party.getPeopleList());

        return dto;
    }
}
