package ru.realityfamily.partyapp.Presentation.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.ViewModel.AuthViewModel;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.AuthBinding;

public class AuthFragment extends Fragment {

    private AuthViewModel mViewModel;
    private AuthBinding mBinding;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        if (getArguments() != null && getArguments().containsKey("token")) {
            if (mViewModel.auth(getArguments().getString("token"))) {
                Navigation.findNavController(((MainActivity) getActivity()).mBinding.navHostFragment).navigate(R.id.action_authFragment_to_partyList);
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = AuthBinding.inflate(inflater, container, false);

        mBinding.vkAuth.setOnClickListener((view) -> ServiceLocator.getInstance().getVK_API().auth.auth((MainActivity) getActivity()));

        mBinding.auth.setOnClickListener((view) -> Navigation.findNavController(((MainActivity) getActivity()).mBinding.navHostFragment).navigate(R.id.action_authFragment_to_partyList));

        return mBinding.getRoot();
    }
}
