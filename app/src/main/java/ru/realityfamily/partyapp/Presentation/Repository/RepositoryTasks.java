package ru.realityfamily.partyapp.Presentation.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;

public interface RepositoryTasks {
    <T extends Party> LiveData<List<T>> getAllParties();
    void addParty(Party party);
    void deleteParty(Party party);
}
