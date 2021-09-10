package com.example.partyapp.Presentation.Repository;

import com.example.partyapp.Presentation.Repository.Mock.MockBase;

public class Repository {
    static RepositoryTasks repository;

    static public RepositoryTasks getRepository() {
        if (repository == null) {
            repository = new MockBase();
        }
        return repository;
    }
}
