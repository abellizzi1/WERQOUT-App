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
     * A JSON array containing all of the search results
     */
    private JSONArray jsonArray;
    /**
     * The filter that is currently being applied to the search results
     */
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
            /**
             * This onclick method filters the search results by athletes
             */
            public void onClick(View view) {
                currentFilter = 0;
                getSearchResults(Const.URL_JSON_REQUEST_ATHLETES + "/all");
            }
        });
        filterCoaches.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by coaches
             */
            public void onClick(View view) {
                currentFilter = 1;
//                getSearchResults(Const.URL_JSON_REQUEST_COACHES);
                getSearchResults(Const.POSTMAN_TEST_URL + "/coaches");
            }
        });
        filterGyms.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This onclick method filters the search results by gym owners
             */
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