package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Presentation.Repository.Repository;

public class PartyListViewModel extends ViewModel {

    public LiveData<List<Party>> getPartyList() {
        return Repository.getRepository().getAllParties();
    }

    public void deleteParty(Party party) {
        Repository.getRepository().deleteParty(party);
    }
}