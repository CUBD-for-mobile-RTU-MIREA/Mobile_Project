package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;

public class PartyListViewModel extends ViewModel {

    public LiveData<List<Party>> getPartyList() {
        switch (ServiceLocator.getInstance().getPerson().getRole()) {
            case Admin:
                return ServiceLocator.getInstance().getRepository().getAllParties();
            case Moder:
                return ServiceLocator.getInstance().getRepository().getNotVerifiedParties();
            case User:
                return ServiceLocator.getInstance().getRepository().getVerifiedParties();
            default:
                return ServiceLocator.getInstance().getRepository().getVerifiedParties();
        }
    }
}