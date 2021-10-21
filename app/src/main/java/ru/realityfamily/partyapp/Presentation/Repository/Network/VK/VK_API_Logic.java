package ru.realityfamily.partyapp.Presentation.Repository.Network.VK;

import java.util.HashMap;
import java.util.Map;

import ru.realityfamily.partyapp.Presentation.Repository.Network.VK.OATH.VK_Auth;

public class VK_API_Logic {
    Map<String, String> api_info = Map.of("v", "5.131");

    public VK_Auth auth;

    public VK_API_Logic() {
        auth = new VK_Auth();
    }

    void getPersonInfo() {

    }
}
