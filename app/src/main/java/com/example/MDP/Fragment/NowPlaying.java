package com.example.MDP.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import com.example.MDP.Adapter.MovieAdapter;
import com.example.MDP.Model.Movies;
import com.example.MDP.R;


public class NowPlaying extends Fragment {
    private RecyclerView rv_nowplaying;
    private ProgressBar progressBar;
    private Button button;

    private MovieAdapter movieAdapter;
    private ArrayList<Movies> movieList = new ArrayList<>();

    public NowPlaying() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        button = view.findViewById(R.id.retry);
        progressBar = view.findViewById(R.id.pb_nowplaying);
        progressBar.setVisibility(View.VISIBLE);

        rv_nowplaying = view.findViewById(R.id.rv_nowplaying);
        rv_nowplaying.setVisibility(View.INVISIBLE);

        rv_nowplaying.setLayoutManager(new LinearLayoutManager(getActivity()));

        getData();

        return view;
    }

    private void getData() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=4cb9ec1672f2d32cd3869b29775f7fc2&language=en-US";

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    String response = new String(responseBody);
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject object = results.getJSONObject(i);
                        Movies movies = new Movies(object);
                        movieList.add(movies);
                    }
                    movieAdapter = new MovieAdapter(getActivity());
                    movieAdapter.setMovieList(movieList);
                    rv_nowplaying.setAdapter(movieAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                rv_nowplaying.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getFragmentManager() != null) {
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                            fragmentTransaction.detach(NowPlaying.this).attach(NowPlaying.this).commit();
                        }
                    }
                });
                Toast.makeText(getContext(),R.string.check_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
