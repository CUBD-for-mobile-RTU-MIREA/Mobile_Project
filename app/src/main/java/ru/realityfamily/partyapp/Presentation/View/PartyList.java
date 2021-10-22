package ru.realityfamily.partyapp.Presentation.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.Domain.Model.Person;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.View.Adapters.PartyListAdapter;
import ru.realityfamily.partyapp.Presentation.ViewModel.PartyListViewModel;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.PartyListFragmentBinding;

public class PartyList extends Fragment {

    private PartyListViewModel mViewModel;
    private PartyListFragmentBinding mBinding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = PartyListFragmentBinding.inflate(getLayoutInflater(), container, false);

        ((MainActivity) requireActivity()).onSupportNavigateUp();

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).setSupportActionBar(mBinding.bottomAppBar);

        mBinding.partyListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        mBinding.fab.setOnClickListener((View v) -> {
            Navigation.findNavController(((MainActivity) requireActivity()).mBinding.navHostFragment).navigate(R.id.action_partyList_to_addParty);
        });


        mBinding.search.setOnClickListener((v) -> {
            mBinding.searchTextField.setVisibility(mBinding.searchTextField.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });

        mBinding.bottomAppBar.setNavigationIcon(R.drawable.menu);
        mBinding.bottomAppBar.setNavigationOnClickListener((View v) -> {
            Toast.makeText(getContext(), "Pressed nav", Toast.LENGTH_LONG).show();
            mBinding.drawer.openDrawer(GravityCompat.START);
        });

        mBinding.bottomAppBar.setOnMenuItemClickListener((item) -> {
            switch (item.getItemId()) {
                case R.id.home:
                    mBinding.drawer.openDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });

        mViewModel = new ViewModelProvider(this).get(PartyListViewModel.class);

        mViewModel.getPartyList().observe(getViewLifecycleOwner(), (List<Party> partyList) -> {
            mBinding.partyListRecycler.setAdapter(new PartyListAdapter(partyList, ((MainActivity) requireActivity())));
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mViewModel = null;
    }
}