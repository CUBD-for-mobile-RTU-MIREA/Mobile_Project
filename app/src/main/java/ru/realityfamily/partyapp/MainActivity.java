package ru.realityfamily.partyapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.net.Uri;
import android.os.Bundle;

import ru.realityfamily.partyapp.Presentation.Repository.Repository;
import ru.realityfamily.partyapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Repository.init(getApplication());
    }

    void chooseImages(String mime, MutableLiveData<Uri> answer) {
        registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                answer.setValue(result);
            }
        }).launch(mime);
    }
}