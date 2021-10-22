package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;

public class PartyViewModel extends ViewModel {

    private Party mParty;

    public Party getParty() {
        return mParty;
    }

    public void verifyParty() {
        if (mParty != null) {
            mParty.setVerified(true);
            ServiceLocator.getInstance().getRepository().updateParty(mParty);
        }
    }

    public void unVerifyParty() {
        if (mParty != null) {
            ServiceLocator.getInstance().getRepository().deleteParty(mParty);
        }
    }

    public void setParty(Party Party) {
        mParty = Party;
    }
}