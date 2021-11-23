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
import com.example.werqoutfrontend.model.Coach;
import com.example.werqoutfrontend.model.User;
import com.example.werqoutfrontend.network.ServerRequest;
import com.example.werqoutfrontend.utils.Const;
import com.example.werqoutfrontend.utils.RecyclerViewAdapter;
import com.example.werqoutfrontend.utils.RecyclerViewComponent;
import com.example.werqoutfrontend.utils.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * This class contains the logic necessary for the search screen. The user can enter their search
 * and a list of results will be filtered and displayed based upon what is entered. User's can also
 * click on search results to have information about the search result to be displayed.
 * @author Colin
 */

public class SearchScreen extends AppCompatActivity {
    /**
     * The Recycler View that is used to display the search results
     */
    private RecyclerView mRecyclerView;
    /**
     * The Adapter that is used to handle various functionality associated with the recycler view
     */
    private RecyclerViewAdapter mAdapter;
    /**
     * A Layout Manager used to handling the layout of views within the recycler component
     */
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * An array list that contains Recycler View Components that will be displayed within the
     * recycler view
     */
    private ArrayList<RecyclerViewComponent> searchResults;
    /**
     * An Array List of JSON objects containing all of the search results
     */
    private ArrayList<JSONObject> jsonObjects;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        Button filterAthletes = findViewById(R.id.filter_athletes_button_search_screen);
        Button filterCoaches = findViewById(R.id.filter_coaches_button_search_screen);
        Button filterGyms = findViewById(R.id.filter_gym_button_serach_screen);
        Button filterEvents = findViewById(R.id.filter_events_button_search_screen);
        Button filterTeams = findViewById(R.id.filter_teams_button_search_screen);
        Button filterAll = findViewById(R.id.filter_all_button_search_screen);

        initializeSearchView();
        getSearchResults(Const.URL_JSON_REQUEST_ATHLETES + "/all", 0);
        getSearchResults(Const.URL_JSON_REQUEST_COACHES,1);
        getSearchResults(Const.URL_JSON_REQUEST_GYMOWNER,2);
        getSearchResults(Const.URL_JSON_REQUEST_EVENTS,3);
        getSearchResults(Const.URL_JSON_REQUEST_TEAMS,4);

        filterAthletes.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by athletes
             */
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_ATHLETES + "/all", 0);
            }
        });
        filterCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by coaches
             */
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_COACHES,1);
            }
        });
        filterGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by gym owners
             */
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_GYMOWNER,2);
            }
        });
        filterEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by gym owners
             */
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_EVENTS,3);
            }
        });
        filterTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by gym owners
             */
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_TEAMS,4);
            }
        });
        filterAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initializeSearchView();
                getSearchResults(Const.URL_JSON_REQUEST_ATHLETES + "/all", 0);
                getSearchResults(Const.URL_JSON_REQUEST_COACHES,1);
                getSearchResults(Const.URL_JSON_REQUEST_GYMOWNER,2);
                getSearchResults(Const.URL_JSON_REQUEST_EVENTS,3);
                getSearchResults(Const.URL_JSON_REQUEST_TEAMS,4);
            }
        });
    }
    private void initializeSearchView(){
        index = 0;
        jsonObjects = new ArrayList<>();
        searchResults = new ArrayList<>();
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
            /**
             * This method updates the search results based upon what the user has currently
             * typed in the search bar.
             */
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void getSearchResults(String url, int filter)
    {
        ServerRequest getAthletes = new ServerRequest();
        getAthletes.jsonArrayRequest(new VolleyCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                //Do nothing
            }

            @Override
            public void onSuccess(JSONArray result) {
                for(int i = 0; i < result.length(); i++)
                {
                    try {
                        JSONObject searchResult = result.getJSONObject(i);
                        jsonObjects.add(searchResult);
                        //Depending on searchType, I'll need to add some conditionals here
                        if(filter == 0) {
                            String username = searchResult.get("userName").toString();
                            String description = searchResult.get("email").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_default_account_box,
                                    username, description, index, filter));
                        }
                        else if(filter == 1)
                        {
                            String username = searchResult.get("userName").toString();
                            String rating = searchResult.get("rating").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_baseline_coach_24,
                                    username, rating, index, filter));
                        }
                        else if(filter == 2)
                        {
                            String gymName = searchResult.get("gymName").toString();
                            String username = searchResult.get("userName").toString();
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_baseline_gym_24,
                                    gymName, username, index, filter));
                        }
                        else if(filter == 3)
                        {
                            String desc = searchResult.get("description").toString();
                            String date = AddDeleteWorkoutScreen.formatDateTimeFromDatabase(searchResult.get("date").toString());
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_event,
                                    desc, date, index, filter));
                        }
                        else if(filter == 4){
                            String teamName = searchResult.getString("name");
                            String desc = searchResult.getString("description");
                            searchResults.add(new RecyclerViewComponent(R.drawable.ic_team,
                                    teamName, desc, index, filter));
                        }
                        index++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                after();
            }
        },url);
    }
    //After the results have all been retrieved from the server, add them to the recycler view to
    //be displayed for the user.
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
            /**
             * This onclick method allows users to click on various search results and then displays
             * information on those search results
             */
            public void onComponentClick(int position) {
                try {
                    JSONObject jsonObject = jsonObjects.get(searchResults.get(position).getOriginalIndex());
                    int resultType = searchResults.get(position).getResultType();
                    if(resultType == 0)
                    {
                        Athlete athlete = new Athlete(jsonObject.getString("email"),null,
                                jsonObject.getString("userName"), Integer.valueOf(jsonObject.get("id").toString()));
                        Intent intent = new Intent(getApplicationContext(), ProfileScreen.class);
                        intent.putExtra("athlete", athlete);
                        intent.putExtra("userType", "athlete");
                        startActivity(intent);
                    }
                    else if(resultType == 1)
                    {
                        String email = jsonObject.getString("email");
                        String username = jsonObject.getString("userName");
                        int id = jsonObject.getInt("id");
                        double rating = jsonObject.getDouble("rating");
                        int numRatings = jsonObject.getInt("numRatings");
                        Coach coach = new Coach(email, null, username, id, rating, numRatings);
                        Intent intent = new Intent(getApplicationContext(), ProfileScreen.class);
                        intent.putExtra("coach",coach);
                        intent.putExtra("userType", "coach");
                        startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}