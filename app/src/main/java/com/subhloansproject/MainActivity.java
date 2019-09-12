package talks.foazo.com.subhloansproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import talks.foazo.com.subhloansproject.data.network.ServerRequest;
import talks.foazo.com.subhloansproject.ui.everyTenthCharacterFragment;
import talks.foazo.com.subhloansproject.ui.onDemand.GenericTaskListener;
import talks.foazo.com.subhloansproject.ui.tenthCharacterFragment;
import talks.foazo.com.subhloansproject.ui.wordCounterFragment;
import talks.foazo.com.subhloansproject.utils.UrlManager;

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
