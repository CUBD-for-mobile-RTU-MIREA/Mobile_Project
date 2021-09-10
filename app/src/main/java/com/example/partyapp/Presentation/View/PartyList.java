package com.example.partyapp.Presentation.View;

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

import com.example.partyapp.Domain.Model.Party;
import com.example.partyapp.Presentation.View.Adapters.PartyListAdapter;
import com.example.partyapp.Presentation.ViewModel.PartyListViewModel;
import com.example.partyapp.R;
import com.example.partyapp.databinding.PartyListFragmentBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_partyList_to_addParty);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mViewModel.deleteParty(position);
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