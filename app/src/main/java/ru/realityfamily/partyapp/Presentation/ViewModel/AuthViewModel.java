package ru.realityfamily.partyapp.Presentation.ViewModel;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.Navigation;

import java.util.concurrent.Executors;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.R;

public class AuthViewModel extends ViewModel {


    public void auth (String token, MainActivity activity) {
        if (token != null) {
            if (ServiceLocator.getInstance().getPerson() == null) {
                ServiceLocator.getInstance().getRepository().findPerson(
                        activity.getPreferences(Context.MODE_PRIVATE).getString("email", ""),
                        activity
                ).observe(activity, (person) -> {
                    if (person != null) {
                        ServiceLocator.getInstance().setPerson(person);
                        Navigation.findNavController(activity.mBinding.navHostFragment).navigate(R.id.action_authFragment_to_partyList);
                    } else {
                        activity.getPreferences(Context.MODE_PRIVATE).edit().remove("email").remove("token").apply();
                    }
                });
            }
        }
    }

    public LiveData<Person> auth (String login, String password, MainActivity activity) {
        ServiceLocator.getInstance().getRepository().findPerson(login, password, activity).observe(activity, (person) -> {
            if (person != null) {
                ServiceLocator.getInstance().setPerson(person);
                activity.getPreferences(Context.MODE_PRIVATE).edit().putString("email", person.getEmail());
            }
        });
        return ServiceLocator.getInstance().getRepository().findPerson(login, password, activity);
    }
}
