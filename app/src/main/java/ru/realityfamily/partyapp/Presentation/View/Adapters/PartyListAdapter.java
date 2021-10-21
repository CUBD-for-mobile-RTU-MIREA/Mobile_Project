package ru.realityfamily.partyapp.Presentation.View.Adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.time.format.DateTimeFormatter;
import java.util.List;

import ru.realityfamily.partyapp.DI.ServiceLocator;
import ru.realityfamily.partyapp.Domain.Model.Party;
import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.PartyListElementBinding;

public class PartyListAdapter extends RecyclerView.Adapter<PartyListAdapter.PartyViewHolder> {
    private List<Party> data;
    private MainActivity mActivity;

    public PartyListAdapter(List<Party> data, MainActivity activity) {
        mActivity = activity;
        this.data = data;
    }

    @NonNull
    @NotNull
    @Override
    public PartyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        PartyListElementBinding binding = PartyListElementBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PartyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PartyViewHolder holder, int position) {
        holder.binding.partyCard.setOnClickListener((View v) -> {
            Bundle bundle = new Bundle();
            String json = ServiceLocator.getInstance().getGson().toJson(data.get(position));
            bundle.putString("Party", json);

            Navigation.findNavController(mActivity.mBinding.navHostFragment)
                    .navigate(R.id.action_partyList_to_partyFragment, bundle);
        });

        holder.binding.partyName.setText(data.get(position).getName());
        if (data.get(position).getCreator() != null) {
            holder.binding.partyCreator.setText(data.get(position).getCreator().getLast_name() + " " + data.get(position).getCreator().getFirst_name());
        }
        if (data.get(position).getPeopleList() != null) {
            holder.binding.partyPeopleCount.setText(data.get(position).getPeopleList().size() + " " +
                    (data.get(position).getMaxPeopleCount() > 0 ? "/ " + data.get(position).getMaxPeopleCount() + " " : "")
                    + "человек");
        }
        holder.binding.partyPlace.setText(data.get(position).getPlace());

        if (data.get(position).getStartTime() != null && data.get(position).getStopTime() != null) {
            holder.binding.partyTime.setText(
                    data.get(position).getStartTime().toLocalDate().equals(data.get(position).getStopTime().toLocalDate())
                            ?
                            data.get(position).getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                                    data.get(position).getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " +
                                    data.get(position).getStopTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                            :
                            data.get(position).getStartTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                                    data.get(position).getStartTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " +
                                    data.get(position).getStopTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " " +
                                    data.get(position).getStopTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
            );
        }

        if (data.get(position).getImages() != null && !data.get(position).getImages().isEmpty()) {
            holder.binding.imageSlider.setVisibility(View.VISIBLE);
            holder.binding.imageSlider.setAdapter(new ImageSliderAdapter(data.get(position).getImages(), false, mActivity));
        } else {
            holder.binding.imageSlider.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<Party> getData() {
        return data;
    }

    class PartyViewHolder extends RecyclerView.ViewHolder{

        PartyListElementBinding binding;

        public PartyViewHolder(PartyListElementBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
