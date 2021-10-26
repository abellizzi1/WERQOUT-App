package com.example.werqoutfrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;
import java.io.Serializable;

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
    private ArrayList<RecyclerViewComponent> searchResults = new ArrayList<>();
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_screen);
        getAthletes();
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

    private void getAthletes()
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
                for(int i = 0; i < result.length(); i++)
                {
                    try {
                        JSONObject athlete = result.getJSONObject(i);
                        String username = athlete.get("userName").toString();
                        String description = athlete.get("email").toString();
                        searchResults.add(new RecyclerViewComponent(R.drawable.ic_default_account_box,
                                username, description, i));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                after();
            }
        },Const.CURRENT_URL);
    }

    private void after()
    {
        mRecyclerView = findViewById(R.id.recyclerView);
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
                    //TODO: Send the object to the profile page and make the view different

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}