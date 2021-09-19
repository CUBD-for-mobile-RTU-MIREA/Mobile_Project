package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

import ru.realityfamily.partyapp.Domain.Model.Party;

public class PartyViewModel extends ViewModel {

    private Party mParty;

    public Party getParty() {
        return mParty;
    }

    public void setParty(Party Party) {
        mParty = Party;
    }
}