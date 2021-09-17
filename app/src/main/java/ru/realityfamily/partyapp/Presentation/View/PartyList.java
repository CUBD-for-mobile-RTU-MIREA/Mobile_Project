package ru.realityfamily.partyapp.Presentation.View;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.View.Adapters.PartyListAdapter;
import ru.realityfamily.partyapp.Presentation.ViewModel.PartyListViewModel;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.PartyListFragmentBinding;

public class PartyList extends Fragment {

    private PartyListViewModel mViewModel;
    private PartyListFragmentBinding mBinding;

    public static PartyList newInstance() {
        return new PartyList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = PartyListFragmentBinding.inflate(getLayoutInflater(), container, false);

        mBinding.partyListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ((MainActivity) getActivity()).mBinding.fab.setImageResource(R.drawable.add);
        ((MainActivity) getActivity()).mBinding.fab.setOnClickListener((View v) -> {
            Navigation.findNavController(((MainActivity) getActivity()).mBinding.navHostFragment).navigate(R.id.action_partyList_to_addParty);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mViewModel.deleteParty(((PartyListAdapter) mBinding.partyListRecycler.getAdapter()).getData().get(position));
            }
        }).attachToRecyclerView(mBinding.partyListRecycler);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PartyListViewModel.class);

        mViewModel.getPartyList().observe(getViewLifecycleOwner(), (List<Party> partyList) -> {
            mBinding.partyListRecycler.setAdapter(new PartyListAdapter(partyList));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mViewModel = null;
    }
}