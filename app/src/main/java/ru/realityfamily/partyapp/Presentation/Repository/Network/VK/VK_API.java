package ru.realityfamily.partyapp.Presentation.Repository.Network.VK;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.realityfamily.partyapp.Domain.Model.Person;

public interface VK_API {
    @GET("account.getProfileInfo")
    Call<VK_API_Logic.APIResponse> getPersonInfo(@QueryMap Map<String, String> api_info, @Query("access_token") String access_token);
}
