package Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pixuredlinux3.photogallery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.GalleryItem;
import model.Photo;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoSingleFragment extends Fragment {
    private static final String API_KEY = "d251af8d8266d103fe45c9539f1df505";
    private static final String TAG = "PhotoSingleFragment";
    private final static String GALLERY_ITEM = "gallery_item";

    private ImageView image, buddyIcon;
    private TextView title;
    private TextView owner;
    private TextView date;
    private TextView location;
    private TextView description;

    private GalleryItem item;
    private Photo photo;
    public PhotoSingleFragment() {
    }

    public static PhotoSingleFragment newInstance(GalleryItem item){
        Bundle args = new Bundle();
        args.putParcelable(GALLERY_ITEM, item);
        PhotoSingleFragment fragment = new PhotoSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_photo_gallery, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sendRequest(query, true);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("Search for item", false);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            item = bundle.getParcelable(GALLERY_ITEM);
        }
        sendRequest(item.getId(), false);
        sendRequestForComments(item.getId(), false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_photo_single, container, false);
        image = (ImageView) v.findViewById(R.id.image);
        buddyIcon = (ImageView) v.findViewById(R.id.buddyImage);
        title = (TextView) v.findViewById(R.id.title);
        owner = (TextView) v.findViewById(R.id.Owner);
       // date = (TextView) v.findViewById(R.id.date);
        location = (TextView) v.findViewById(R.id.location);

        if(!item.getTitle().equals(""))
            title.setText(item.getTitle());
        else title.setText("Untitled");
        owner.setText("loading...");
        location.setText("loading...");

        Picasso.with(getActivity())
                .load(item.getUrl_s())
                .into(image);

        Picasso.with(getActivity())
                .load("https://www.flickr.com/images/buddyicon.gif")
                .into(buddyIcon);
        return v;
    }

    public void sendRequest(final String query, final boolean isSearching){
        String url = "https://api.flickr.com/services/rest/";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        if(query == null)
            params.put("method", "flickr.photos.getRecent");
        else if(isSearching){
            params.put("method", "flickr.photos.search");
            params.put("text", query);
        }
        else {
            params.put("method", "flickr.photos.getInfo");
            params.put("photo_id", query);
        }
        params.put("api_key", API_KEY);
        params.put("format", "json");
        params.put("nojsoncallback", "1");
        params.put("extras", "url_s");

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Type listType = new TypeToken<List<GalleryItem>>(){}.getType();
                Gson gson = new GsonBuilder().create();
                Log.d(TAG, response.toString());
                try {
                    JSONObject photosJsonObject = response.getJSONObject("photo");
                    photo = gson.fromJson(photosJsonObject.toString(), Photo.class);
                    updateView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*if(query == null || isSearching){
                    try {
                        JSONObject photosJsonObject = response.getJSONObject("photos");
                        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");
                        mItems = gson.fromJson(photoJsonArray.toString(), listType);
                        setupAdapter(mItems);

                    } catch (JSONException e) {
                        Log.d(TAG, "No photo found");
                        e.printStackTrace();
                    }
                }
                else {
                }*/
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }


        });
    }

    public void sendRequestForComments(final String query, final boolean isSearching){
        String url = "https://api.flickr.com/services/rest/";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("method", "flickr.photos.comments.getList");
        params.put("photo_id", query);
        params.put("api_key", API_KEY);
        params.put("format", "json");
        params.put("nojsoncallback", "1");
        params.put("extras", "url_s");

        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Type listType = new TypeToken<List<GalleryItem>>(){}.getType();
                Gson gson = new GsonBuilder().create();
                Log.d("Comments" , response.toString());
                /*try {
                    JSONObject photosJsonObject = response.getJSONObject("photo");
                    photo = gson.fromJson(photosJsonObject.toString(), Photo.class);
                    updateWithComments();
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }
    private void updateView() {
        if(!photo.getTitle().equals(""))
            title.setText(photo.getTitle().get_content());
        owner.setText("by " + photo.getOwner().getUsername());
        location.setText(photo.getOwner().getLocation());
        //date.setText(photo.getDate().getPosted());
        int iconfarm = photo.getOwner().getIconfarm();
        String iconserver = photo.getOwner().getIconserver();
        String nsid= photo.getOwner().getNsid();
        String url = "http://farm" + iconfarm + ".staticflickr.com/" + iconserver + "/buddyicons/" + nsid + ".jpg";

        if(iconfarm > 0){
            Picasso.with(getActivity())
                    .load(url)
                    .into(buddyIcon);
        }
        else{
            Picasso.with(getActivity())
                    .load("https://www.flickr.com/images/buddyicon.gif")
                    .into(buddyIcon);
        }
    }

    private void updateWithComments(){

    }
}
