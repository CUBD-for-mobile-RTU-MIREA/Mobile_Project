package ru.realityfamily.partyapp.Presentation.Repository.Model;

import androidx.room.Entity;

import ru.realityfamily.partyapp.Domain.Model.Person;

@Entity(tableName = "people", primaryKeys = {"id"}, ignoredColumns = {"connections"})
public class PeopleDTO extends Person {
}
