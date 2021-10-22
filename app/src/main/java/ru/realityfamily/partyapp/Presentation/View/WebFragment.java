package ru.realityfamily.partyapp.Presentation.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.databinding.WebFragmentBinding;

public class WebFragment extends Fragment{

    private WebFragmentBinding mBinding;
    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = WebFragmentBinding.inflate(inflater, container, false);

        if (url != null && !url.isEmpty()) {
            CookieManager.getInstance().removeAllCookies(null);
            mBinding.Web.clearCache(true);
            mBinding.Web.loadUrl(url);
            mBinding.Web.setWebViewClient(ServiceLocator.getInstance().getVK_API().auth.oath2VK((MainActivity) getActivity()));
        }

        return mBinding.getRoot();
    }
}
