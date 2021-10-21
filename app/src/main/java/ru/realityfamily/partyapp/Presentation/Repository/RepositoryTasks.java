package ru.realityfamily.partyapp.Presentation.Repository;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;

public interface RepositoryTasks {
    <T extends Party> LiveData<List<T>> getAllParties();
    void addParty(Party party);
    void deleteParty(Party party);
    <T extends Party> LiveData<T> findParty(String id, LifecycleOwner owner);

    <T extends Person> LiveData<T> findPerson(String email, LifecycleOwner owner);
    <T extends Person> LiveData<T> findPerson(String email, String password, LifecycleOwner owner);
    void addPerson(Person person);
    void updatePerson(Person person);
}
