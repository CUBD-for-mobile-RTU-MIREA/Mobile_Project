package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;

public class PartyListViewModel extends ViewModel {

    public LiveData<List<Party>> getPartyList() {
        return ServiceLocator.getInstance().getRepository().getAllParties();
    }

    public void deleteParty(Party party) {
        ServiceLocator.getInstance().getRepository().deleteParty(party);
    }
}