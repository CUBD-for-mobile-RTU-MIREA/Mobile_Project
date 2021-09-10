package com.example.partyapp.Presentation.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Presentation.Repository.Repository;

import java.util.List;

public class PartyListViewModel extends ViewModel {

    public MutableLiveData<List<Party>> getPartyList() {
        return Repository.getRepository().getAllParties();
    }

    public void deleteParty(int position) {
        Repository.getRepository().deleteParty(position);
    }
}