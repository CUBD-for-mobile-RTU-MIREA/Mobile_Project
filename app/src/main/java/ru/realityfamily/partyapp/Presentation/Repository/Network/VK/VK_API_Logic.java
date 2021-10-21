package ru.realityfamily.partyapp.Presentation.Repository.Network.VK;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.Repository.Network.VK.OATH.VK_Auth;

public class VK_API_Logic {
    Map<String, String> api_info = Map.of("v", "5.131");

    public VK_Auth auth;

    public VK_API_Logic() {
        auth = new VK_Auth();
    }

    public void getPersonInfo(String token, MainActivity activity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        VK_API api = retrofit.create(VK_API.class);

        api.getPersonInfo(api_info, token).enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                if (response.isSuccessful()) {
                    ServiceLocator.getInstance().getPerson().setFirst_name(response.body().response.first_name);
                    ServiceLocator.getInstance().getPerson().setLast_name(response.body().response.last_name);
                    ServiceLocator.getInstance().getPerson().getConnections().put("vk", "https://vk.com/" + response.body().response.screen_name);
                    ServiceLocator.getInstance().getPerson().setRole(Person.Role.User);

                    ServiceLocator.getInstance().getRepository().findPerson(ServiceLocator.getInstance().getPerson().getEmail(), activity).observe(activity, (person) -> {
                        if (person == null) {
                            ServiceLocator.getInstance().getRepository().addPerson(ServiceLocator.getInstance().getPerson());
                        } else {
                            if (!person.getFirst_name().equals(ServiceLocator.getInstance().getPerson().getFirst_name()) ||
                                    !person.getLast_name().equals(ServiceLocator.getInstance().getPerson().getFirst_name()) ||
                                    !person.getConnections().equals(ServiceLocator.getInstance().getPerson().getConnections()) ||
                                    !person.getPhone().equals(ServiceLocator.getInstance().getPerson().getPhone()) ||
                                    person.getRole() != ServiceLocator.getInstance().getPerson().getRole()) {
                                person.setFirst_name(ServiceLocator.getInstance().getPerson().getFirst_name());
                                person.setLast_name(ServiceLocator.getInstance().getPerson().getLast_name());
                                person.setPhone(ServiceLocator.getInstance().getPerson().getPhone());
                                person.setRole(Person.Role.User);
                                person.getConnections().put("vk", ServiceLocator.getInstance().getPerson().getConnections().get("vk"));

                                ServiceLocator.getInstance().getRepository().updatePerson(person);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {

            }
        });
    }

    public class APIResponse {
        public class APIPerson {
            public String first_name;
            public String last_name;
            public int id;
            public String screen_name;
        }

        public APIPerson response;
    }
}
