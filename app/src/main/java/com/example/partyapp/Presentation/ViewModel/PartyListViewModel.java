package com.example.partyapp.Presentation.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Presentation.Repository.Repository;

import java.util.List;

public class PartyListViewModel extends ViewModel {

    public LiveData<List<Party>> getPartyList() {
        return Repository.getRepository().getAllParties();
    }

    public void deleteParty(Party party) {
        Repository.getRepository().deleteParty(party);
    }
}