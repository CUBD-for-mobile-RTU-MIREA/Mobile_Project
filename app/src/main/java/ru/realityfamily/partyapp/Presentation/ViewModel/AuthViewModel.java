package ru.realityfamily.partyapp.Presentation.ViewModel;

import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {


    public boolean auth (String token) {
        if (token != null) {
            return true;
        }
        return false;
    }

    public boolean auth (String login, String password) {
        if (login.contains("admin") && password.equals("admin")) {
            return true;
        }
        else if (login.contains("moder") && password.equals("moder")) {
            return true;
        }
        return false;
    }
}
