package ru.realityfamily.partyapp.Presentation.Repository.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;

@Entity(tableName = "party", primaryKeys = {"id"}, ignoredColumns = {"creator", "startTime", "stopTime", "peopleList", "images"})
public class PartyDTO extends Party {
    @ColumnInfo
    private String creatorDTO;
    @ColumnInfo
    private String startTimeDTO;
    @ColumnInfo
    private String stopTimeDTO;
    @ColumnInfo
    private String peopleListDTO;
    @ColumnInfo
    private String imagesDTO;


    public String getCreatorDTO() {
        return creatorDTO;
    }

    public String getStartTimeDTO() {
        return startTimeDTO;
    }

    public String getStopTimeDTO() {
        return stopTimeDTO;
    }

    public String getPeopleListDTO() {
        return peopleListDTO;
    }

    public String getImagesDTO() {
        return imagesDTO;
    }

    public void setCreatorDTO(String creatorDTO) {
        this.creatorDTO = creatorDTO;
        super.setCreator(new Gson().fromJson(this.creatorDTO, Person.class));
    }

    public void setStartTimeDTO(String startTimeDTO) {
        this.startTimeDTO = startTimeDTO;
        super.setStartTime(LocalDateTime.parse(this.startTimeDTO, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    public void setStopTimeDTO(String stopTimeDTO) {
        this.stopTimeDTO = stopTimeDTO;
        super.setStopTime(LocalDateTime.parse(this.stopTimeDTO, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
    }

    public void setPeopleListDTO(String peopleListDTO) {
        this.peopleListDTO = peopleListDTO;
        super.setPeopleList(new Gson().fromJson(this.peopleListDTO, List.class));
    }

    public void setImagesDTO(String imagesDTO) {
        this.imagesDTO = imagesDTO;
        super.setImages(new Gson().fromJson(this.imagesDTO, List.class));
    }

    @Override
    public Person getCreator() {
        if (super.getCreator() == null) {
            super.setCreator(new Gson().fromJson(this.creatorDTO, Person.class));
        }
        return super.getCreator();
    }

    @Override
    public void setCreator(Person creator) {
        super.setCreator(creator);
        this.creatorDTO = new Gson().toJson(creator);
    }

    @Override
    public LocalDateTime getStartTime() {
        if (super.getStartTime() == null) {
            if (this.startTimeDTO != null) {
                super.setStartTime(LocalDateTime.parse(this.startTimeDTO, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            } else {
                return null;
            }
        }
        return super.getStartTime();
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        super.setStartTime(startTime);
        if (startTime != null) {
            this.startTimeDTO = startTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            this.startTimeDTO = null;
        }
    }

    @Override
    public LocalDateTime getStopTime() {
        if (super.getStopTime() == null) {
            if (this.stopTimeDTO != null) {
                super.setStopTime(LocalDateTime.parse(this.stopTimeDTO, DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
            } else {
                return null;
            }
        }
        return super.getStopTime();
    }

    @Override
    public void setStopTime(LocalDateTime stopTime) {
        super.setStopTime(stopTime);
        if (stopTime != null) {
            this.stopTimeDTO = stopTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        } else {
            this.stopTimeDTO = null;
        }
    }

    @Override
    public List<Person> getPeopleList() {
        if (super.getPeopleList() == null || super.getPeopleList().isEmpty()) {
            super.setPeopleList(new Gson().fromJson(this.peopleListDTO, List.class));
        }
        return super.getPeopleList();
    }

    @Override
    public void setPeopleList(List<Person> peopleList) {
        super.setPeopleList(peopleList);
        this.peopleListDTO = new Gson().toJson(peopleList);
    }

    @Override
    public List<String> getImages() {
        if (super.getImages() == null || super.getImages().isEmpty()) {
            super.setImages(new Gson().fromJson(this.imagesDTO, List.class));
        }
        return super.getImages();
    }

    @Override
    public void setImages(List<String> images) {
        super.setImages(images);
        this.imagesDTO = new Gson().toJson(images);
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
        dto.setImages(party.getImages());

        return dto;
    }
}
