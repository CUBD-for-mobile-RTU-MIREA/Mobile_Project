package com.example.partyapp.Presentation.Repository;

import androidx.lifecycle.MutableLiveData;

import com.example.partyapp.Domain.Model.Party;

import java.util.List;

public interface RepositoryTasks {
    MutableLiveData<List<Party>> getAllParties();
    void addParty(Party party);
    void deleteParty(int position);
}
