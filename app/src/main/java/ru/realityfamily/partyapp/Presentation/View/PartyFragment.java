package ru.realityfamily.partyapp.Presentation.View;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.View.Adapters.ImageSliderAdapter;
import ru.realityfamily.partyapp.Presentation.ViewModel.PartyViewModel;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.PartyFragmentBinding;

public class PartyFragment extends Fragment {

    private PartyViewModel mViewModel;
    private PartyFragmentBinding mBinding;

    public static PartyFragment newInstance() {
        return new PartyFragment();
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(PartyViewModel.class);

        if (getArguments() != null) {
            mViewModel.setParty(
                    ServiceLocator.getInstance().getGson().fromJson(getArguments().getString("Party"), Party.class)
            );
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = PartyFragmentBinding.inflate(inflater, container, false);

        ((MainActivity) getActivity()).setSupportActionBar(mBinding.toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mBinding.toolbar.setNavigationOnClickListener((View v) -> {
            Navigation.findNavController(v).popBackStack();
        });

        if (mViewModel.getParty() != null) {
            switch (ServiceLocator.getInstance().getPerson().getRole()) {
                case Admin:
                    break;
                case Moder:
                    mBinding.moderFABs.setVisibility(View.VISIBLE);
                    mBinding.fab.setVisibility(View.GONE);

                    mBinding.fabCheck.setOnClickListener((v) -> {
                        mViewModel.verifyParty();
                    });
                    mBinding.fabCross.setOnClickListener((v) -> {
                        mViewModel.unVerifyParty();
                    });
                    break;
                case User:
                    mBinding.moderFABs.setVisibility(View.GONE);
                    mBinding.fab.setVisibility(View.VISIBLE);

                    mBinding.fab.setOnClickListener((View v) -> {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "app://rf.party_app/" + mViewModel.getParty().getId());
                        sendIntent.setType("text/html");

                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    });
                    break;
            }

            mBinding.imageSlider.setAdapter(new ImageSliderAdapter(mViewModel.getParty().getImages(), false, ((MainActivity) requireActivity())));
            mBinding.toolbarLayout.setTitle(mViewModel.getParty().getName());
            mBinding.partyDescription.setText(mViewModel.getParty().getDescription());
            mBinding.partyPlace.setText(mViewModel.getParty().getPlace());

            if (mViewModel.getParty().getStartTime() != null) {
                mBinding.partyStart.setText(mViewModel.getParty().getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            } else {
                mBinding.partyStart.setText("Не указано");
            }
            if (mViewModel.getParty().getStopTime() != null) {
                mBinding.partyStop.setText(mViewModel.getParty().getStopTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            } else {
                mBinding.partyStop.setText("Не указано");
            }
        }

        return mBinding.getRoot();
    }

}