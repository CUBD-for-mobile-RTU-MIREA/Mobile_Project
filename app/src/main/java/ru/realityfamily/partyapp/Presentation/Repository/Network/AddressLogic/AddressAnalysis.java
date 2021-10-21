package ru.realityfamily.partyapp.Presentation.Repository.Network.AddressLogic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.realityfamily.partyapp.BuildConfig;

public class AddressAnalysis {

    private DaDataAPI api;

    public AddressAnalysis() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suggestions.dadata.ru/suggestions/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(DaDataAPI.class);
    }

    public LiveData<List<String>> getAddressesFromPattern(String pattern) {
        MutableLiveData<List<String>> addresses = new MutableLiveData<>();

        api.listAddresses(new AddressRequest(pattern), "Token ".concat(BuildConfig.DADATA_API_KEY)).enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    addresses.setValue(response.body().suggestions.stream().map(address -> address.value).collect(Collectors.toList()));
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return addresses;
    }

    static class AddressRequest{
        int count;
        String query;

        public AddressRequest(String query) {
            this.count = 5;
            this.query = query;
        }
    }

    static class AddressResponse{

        static class Address{
            String value;
            String unrestricted_value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getUnrestricted_value() {
                return unrestricted_value;
            }

            public void setUnrestricted_value(String unrestricted_value) {
                this.unrestricted_value = unrestricted_value;
            }
        }

        List<Address> suggestions;

        public List<Address> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<Address> suggestions) {
            this.suggestions = suggestions;
        }
    }
}
