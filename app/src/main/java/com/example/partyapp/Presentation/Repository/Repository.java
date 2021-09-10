package com.example.partyapp.Presentation.Repository;

import android.app.Application;

import com.example.partyapp.Presentation.Repository.Mock.MockBase;
import com.example.partyapp.Presentation.Repository.Room.PartyRepository;

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
