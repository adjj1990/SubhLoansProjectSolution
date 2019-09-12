package com.subhloansproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.HashMap;

import com.subhloansproject.main.R;
import com.subhloansproject.main.network.ServerRequest;
import com.subhloansproject.main.ui.everyTenthCharacterFragment;
import com.subhloansproject.main.ui.onDemand.GenericTaskListener;
import com.subhloansproject.main.ui.tenthCharacterFragment;
import com.subhloansproject.main.ui.wordCounterFragment;
import com.subhloansproject.main.utils.UrlManager;

public class MainActivity extends AppCompatActivity implements GenericTaskListener {

    private FrameLayout tenthCharacterHolder;
    private FrameLayout everyTenthCharacterHolder;
    private FrameLayout wordCounterHolder;
    private Button initiateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Class.forName("android.os.AsyncTask");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);

        tenthCharacterHolder = findViewById(R.id.tenthCharacterHolder);
        everyTenthCharacterHolder = findViewById(R.id.everyTenthCharacterHolder);
        wordCounterHolder = findViewById(R.id.wordCounterHolder);
        initiateButton = findViewById(R.id.initiateButton);

        initiateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData();
            }
        });

    }

    private void fetchData(){
        ServerRequest ServerRequest = new ServerRequest();

        ServerRequest.requestPageData(UrlManager.about_url, new HashMap<String, String>(), MainActivity.this, false, "tenthCharacter");
        ServerRequest.requestPageData(UrlManager.index_url, new HashMap<String, String>(), MainActivity.this, false, "everyTenthCharacter");
        ServerRequest.requestPageData(UrlManager.contact_url, new HashMap<String, String>(), MainActivity.this, false, "wordCounter");
    }

    public void generateFragment(String data, String fragment){

        FragmentTransaction basic_tr = getFragmentManager().beginTransaction();

        Bundle basic_bundle = new Bundle();
        basic_bundle.putString("data", data);

        switch (fragment){
            case "tenthCharacter":
                tenthCharacterFragment tenthCharacterFragment = new tenthCharacterFragment();
                tenthCharacterFragment.setArguments(basic_bundle);

                tenthCharacterHolder.setVisibility(View.VISIBLE);

                basic_tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                basic_tr.replace(R.id.tenthCharacterHolder, tenthCharacterFragment);
                basic_tr.addToBackStack(null);
                basic_tr.commit();

                break;

            case "everyTenthCharacter":
                everyTenthCharacterFragment everyTenthCharacterFragment = new everyTenthCharacterFragment();
                everyTenthCharacterFragment.setArguments(basic_bundle);

                tenthCharacterHolder.setVisibility(View.VISIBLE);

                basic_tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                basic_tr.replace(R.id.everyTenthCharacterHolder, everyTenthCharacterFragment);
                basic_tr.addToBackStack(null);
                basic_tr.commit();

                break;

            case "wordCounter":
                wordCounterFragment wordCounterFragment = new wordCounterFragment();
                wordCounterFragment.setArguments(basic_bundle);

                tenthCharacterHolder.setVisibility(View.VISIBLE);

                basic_tr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                basic_tr.replace(R.id.wordCounterHolder, wordCounterFragment);
                basic_tr.addToBackStack(null);
                basic_tr.commit();

                break;

        }
    }

    @Override
    public void updateResult(String result, String token) {
        generateFragment(result, token);
    }
}
