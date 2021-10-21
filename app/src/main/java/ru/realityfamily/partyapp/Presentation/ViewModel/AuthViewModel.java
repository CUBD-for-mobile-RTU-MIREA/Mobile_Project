package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.Executors;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Person;

public class AuthViewModel extends ViewModel {


    public boolean auth (String token) {
        if (token != null) {
            return true;
        }
        return false;
    }

    public LiveData<Person> auth (String login, String password, LifecycleOwner owner) {
        return ServiceLocator.getInstance().getRepository().findPerson(login, password, owner);
    }
}
