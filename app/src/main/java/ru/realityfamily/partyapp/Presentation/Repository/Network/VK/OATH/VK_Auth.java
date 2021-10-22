package ru.realityfamily.partyapp.Presentation.Repository.Network.VK.OATH;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.navigation.Navigation;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.R;

public class VK_Auth {
    public void auth(MainActivity activity) {
        Bundle bundle = new Bundle();
        bundle.putString("url", "https://oauth.vk.com/authorize?client_id=7975999&scope=email&redirect_uri=https://oauth.vk.com/blank.html&display=mobile&response_type=token&scope=offline, email");

        Navigation.findNavController(activity.mBinding.navHostFragment).navigate(R.id.action_authFragment_to_webFragment, bundle);
    }

    public WebViewClient oath2VK(MainActivity activity) {
        return new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (request.getUrl().toString().contains("https://oauth.vk.com/blank.html#")) {
                    String token = Uri.parse(request.getUrl().toString().replace("#", "?")).getQueryParameter("access_token");
                    String email = Uri.parse(request.getUrl().toString().replace("#", "?")).getQueryParameter("email");

                    ServiceLocator.getInstance().getRepository().findPerson(email, activity).observe(activity, (person) -> {
                        if (person == null) {
                            Person newPerson = new Person();
                            newPerson.setEmail(email);

                            ServiceLocator.getInstance().setPerson(newPerson);
                        } else {
                            ServiceLocator.getInstance().setPerson(person);
                        }
                    });
                    ServiceLocator.getInstance().getVK_API().getPersonInfo(token, activity);
                    
                    //activity.getPreferences(Context.MODE_PRIVATE).edit().putString("token", token).putString("email", email).apply();
                    
                    Navigation.findNavController(activity.mBinding.navHostFragment).navigate(R.id.action_webFragment_to_authFragment);

                    return false;
                }
                view.loadUrl(request.getUrl().toString());

                return true;
            }
        };
    }
}
