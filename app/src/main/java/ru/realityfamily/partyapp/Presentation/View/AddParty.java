package ru.realityfamily.partyapp.Presentation.View;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.realityfamily.partyapp.MainActivity;
import ru.realityfamily.partyapp.Presentation.View.Adapters.ImageSliderAdapter;
import ru.realityfamily.partyapp.Presentation.ViewModel.AddPartyViewModel;
import ru.realityfamily.partyapp.R;
import ru.realityfamily.partyapp.databinding.AddPartyFragmentBinding;

public class AddParty extends Fragment {

    private AddPartyViewModel mViewModel;
    private AddPartyFragmentBinding mBinding;

    private LocalDateTime startTime = null;
    private LocalDateTime stopTime = null;
    private List<String> images = new ArrayList<>();

    public static AddParty newInstance() {
        return new AddParty();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = AddPartyFragmentBinding.inflate(getLayoutInflater(), container, false);

        mBinding.backButton.setOnClickListener((View v) -> {
            Navigation.findNavController(v).popBackStack();
        });

        ((MainActivity) getActivity()).mBinding.fab.setImageResource(R.drawable.save);
        ((MainActivity) getActivity()).mBinding.fab.setOnClickListener((View v) -> {
            if (!mBinding.partyName.getText().toString().isEmpty()) {
                mViewModel.AddParty(
                        mBinding.partyName.getText().toString(),
                        mBinding.partyMaxPeopleCount.getText().toString(),
                        mBinding.partyPlace.getText().toString(),
                        mBinding.partyDescription.getText().toString(),
                        startTime,
                        stopTime
                );

                Navigation.findNavController(v).popBackStack();
            } else {
                Toast.makeText(getContext(), "Вы ввели не все данные", Toast.LENGTH_SHORT).show();
            }
        });

        mBinding.partyStart.setOnClickListener((View v) -> {
            final Calendar calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);


                            LocalDateTime temp = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
                            if (stopTime != null && temp.compareTo(stopTime) > 0) {
                                Toast.makeText(getContext(), "Вы выбрали время начала мероприятия позже выбранного время окончания", Toast.LENGTH_LONG).show();
                                return;
                            }
                            startTime = temp;
                            mBinding.partyStart.setText(startTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                        }
                    };

                    new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            };

            new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        mBinding.partyStop.setOnClickListener((View v) -> {
            final Calendar calendar = Calendar.getInstance();

            DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);

                            LocalDateTime temp = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
                            if (startTime != null && temp.compareTo(startTime) < 0) {
                                Toast.makeText(getContext(), "Вы выбрали время окончания мероприятия раньше выбранного времени начала", Toast.LENGTH_LONG).show();
                                return;
                            }
                            stopTime = temp;
                            mBinding.partyStop.setText(stopTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
                        }
                    };

                    new TimePickerDialog(getContext(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
                }
            };

            new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        mBinding.imagesLayout.setOnClickListener((View v) -> {
            if (mBinding.imageSlider.getVisibility() == View.GONE) {
                mBinding.imageSlider.setVisibility(View.VISIBLE);
                mBinding.imageDropdownArrow.setImageResource(R.drawable.arrow_up);
            } else {
                mBinding.imageSlider.setVisibility(View.GONE);
                mBinding.imageDropdownArrow.setImageResource(R.drawable.arrow_down);
            }
        });

        mBinding.imageSlider.setAdapter(new ImageSliderAdapter(images, true, requireActivity().getActivityResultRegistry()));
        mBinding.imageSlider.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddPartyViewModel.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
        mViewModel = null;
    }
}