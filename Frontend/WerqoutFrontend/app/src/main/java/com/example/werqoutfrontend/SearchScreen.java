package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.werqoutfrontend.model.Athlete;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.RecyclerViewAdapter;
import com.example.werqoutfrontend.utils.RecyclerViewComponent;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class SearchScreen extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RecyclerViewComponent> searchResults;
    private JSONArray jsonArray;
    private int currentFilter = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Button filterAthletes = findViewById(R.id.filter_athletes_button_search_screen);
        Button filterCoaches = findViewById(R.id.filter_coaches_button_search_screen);
        Button filterGyms = findViewById(R.id.filter_gym_button_serach_screen);
        getSearchResults(Const.POSTMAN_TEST_URL);

        filterAthletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFilter = 0;
                getSearchResults(Const.URL_JSON_REQUEST_ATHLETES);
            }
        });
        filterCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFilter = 1;
//                getSearchResults(Const.URL_JSON_REQUEST_COACHES);
                getSearchResults(Const.POSTMAN_TEST_URL + "/coaches");
            }
        });
        filterGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFilter = 2;
//                getSearchResults(Const.URL_JSON_REQUEST_GYMOWNER);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        SearchView searchView = findViewById(R.id.search_entry);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getSearchResults(String url)
    {
        ServerRequest getAthletes = new ServerRequest();
        getAthletes.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                //Do nothing
            }

            @Override
            public void onSuccess(JSONArray result) {
                jsonArray = result;
                searchResults = new ArrayList<>();
                for(int i = 0; i < result.length(); i++)
                {
                    try {
                        JSONObject searchResult = result.getJSONObject(i);
                        //Depending on searchType, I'll need to add some conditionals here
                        if(currentFilter == 0) {
                            String username = searchResult.get("userName").toString();
                            String description = searchResult.get("email").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_default_account_box,
                                    username, description, i));
                        }
                        else if(currentFilter == 1)
                        {
                            String username = searchResult.get("userName").toString();
                            String rating = searchResult.get("rating").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_baseline_coach_24,
                                    username, rating, i));
                        }
                        else if(currentFilter == 2)
                        {
                            String gymName = searchResult.get("gymName").toString();
                            String username = searchResult.get("userName").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_baseline_gym_24,
                                    gymName, username, i));
                        }
                        else
                        {
                            String username = searchResult.get("userName").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_default_account_box,
                                    username, "", i));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                after();
            }
        },url);
    }

    private void after()
    {
        mRecyclerView = findViewById(R.id.recyclerView_search_screen);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new RecyclerViewAdapter(searchResults);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnComponentClickListener(new RecyclerViewAdapter.OnComponentClickListener() {
            @Override
            public void onComponentClick(int position) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(
                            searchResults.get(position).getOriginalIndex());
                    Athlete athlete = new Athlete(jsonObject.getString("email"),null,
                            jsonObject.getString("userName"), Integer.valueOf(jsonObject.get("id").toString()));
                    Intent intent = new Intent(getApplicationContext(), ProfileScreen.class);
                    intent.putExtra("athlete", athlete);
                    intent.putExtra("calledFrom", "search");
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}