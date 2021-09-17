package ru.realityfamily.partyapp.Presentation.Repository;

import android.app.Application;

import ru.realityfamily.partyapp.Presentation.Repository.Mock.MockBase;
import ru.realityfamily.partyapp.Presentation.Repository.Room.PartyRepository;

public class Repository {
    static RepositoryTasks repository;

    static public void init(Application application) {
        if (repository == null) {
            repository = new PartyRepository(application);
        }
    }

    static public RepositoryTasks getRepository() {
        if (repository == null) {
            repository = new MockBase();
        }
        return repository;
    }
}
