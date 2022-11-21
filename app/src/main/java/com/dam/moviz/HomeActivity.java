package com.dam.moviz;

import static com.dam.moviz.commons.Constants.COLLECTION_FILMS;
import static com.dam.moviz.commons.Constants.FILE_PREFS;
import static com.dam.moviz.commons.Constants.KEY_PREFS;
import static com.dam.moviz.commons.Constants.KEY_TITRE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dam.moviz.commons.Constants;
import com.dam.moviz.commons.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private View baseView;
    private EditText etSearch;
    private Button btnSearch;

    private RecyclerView rvMovies;
    private Context context;
    private AdapterMovies adapterMovies;
    private FirebaseFirestore db;

//    private ArrayList<ModelMovie> movies;

    private void initUI(){
        baseView = findViewById(R.id.homeLayout);
        context = getApplicationContext();
        rvMovies = findViewById(R.id.recyclerView);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        db = FirebaseFirestore.getInstance();

        btnSearch = findViewById(R.id.btnSearch);
        etSearch = findViewById(R.id.etSearch);
    }

    private void getDataFromFirestore(){
        Query query = db.collection(COLLECTION_FILMS).orderBy(KEY_TITRE);
        FirestoreRecyclerOptions<ModelMovie> movies = new FirestoreRecyclerOptions.Builder<ModelMovie>()
                .setQuery(query, ModelMovie.class)
                .build();
        adapterMovies = new AdapterMovies(movies);
        rvMovies.setAdapter(adapterMovies);
        adapterMovies.startListening();
    }

    private void addSampleData(){
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_PREFS, Context.MODE_PRIVATE);
         if (!sharedPreferences.getBoolean(KEY_PREFS , false))
            AddSampleDataToFireBase.addDataToFireBase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        addSampleData();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Utils.showSnackBar(baseView, "Hello " + currentUser.getDisplayName());
        getDataFromFirestore();
    }
}