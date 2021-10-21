package ru.realityfamily.partyapp.DI;

import android.app.Application;
import android.app.Person;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ru.realityfamily.partyapp.Presentation.Repository.Mock.MockBase;
import ru.realityfamily.partyapp.Presentation.Repository.Network.AddressLogic.AddressAnalysis;
import ru.realityfamily.partyapp.Presentation.Repository.Network.VK.OATH.VK_Auth;
import ru.realityfamily.partyapp.Presentation.Repository.Network.VK.VK_API_Logic;
import ru.realityfamily.partyapp.Presentation.Repository.RepositoryTasks;
import ru.realityfamily.partyapp.Presentation.Repository.Room.PartyRepository;

public class ServiceLocator {
    private static ServiceLocator instance = null;

    private ServiceLocator() {};

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    private Gson mGson;
    private RepositoryTasks mRepository;
    private AddressAnalysis mAnalysis;
    private Person mPerson;
    private VK_API_Logic mVK_API;

    public Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(
                            LocalDateTime.class,
                            (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> LocalDateTime.parse(
                                    json.getAsString(),
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                            )
                    )
                    .registerTypeAdapter(
                            LocalDateTime.class,
                            (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> new JsonPrimitive(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(src))
                    )
                    .create();
        }
        return mGson;
    }

    public void initBase(Application app) {
        if (mRepository == null) {
            mRepository = new PartyRepository(app);
        }
    }

    public RepositoryTasks getRepository() {
        if (mRepository == null) {
            mRepository = new MockBase();
        }
        return mRepository;
    }

    public AddressAnalysis getAnalysis() {
        if (mAnalysis == null) {
            mAnalysis = new AddressAnalysis();
        }
        return mAnalysis;
    }

    public VK_API_Logic getVK_API() {
        if (mVK_API == null) {
            mVK_API = new VK_API_Logic();
        }
        return mVK_API;
    }

    public Person getPerson() {
        return mPerson;
    }

    public void setPerson(Person person) {
        this.mPerson = person;
    }
}
