package com.example.partyapp.Presentation.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Presentation.Repository.Model.PartyDTO;

import java.util.List;

public interface RepositoryTasks {
    <T extends Party> LiveData<List<T>> getAllParties();
    <T extends Party> void addParty(T party);
    <T extends Party> void deleteParty(T party);
}
