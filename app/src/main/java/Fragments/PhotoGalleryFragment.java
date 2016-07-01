package Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pixuredlinux3.photogallery.PhotoSingleActivity;
import com.example.pixuredlinux3.photogallery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import model.GalleryItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoGalleryFragment extends Fragment {

    private static final String API_KEY = "d251af8d8266d103fe45c9539f1df505";
    private static final String TAG = "PhotoGalleryFragment";
    private RecyclerView mPhotoRecyclerView;
    private List<GalleryItem> mItems = new ArrayList<>();
    private GalleryItem item;
    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        sendRequest(null, false);
        Log.i(TAG, "Background thread started");
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        mPhotoRecyclerView = (RecyclerView) v
                .findViewById(R.id.fragment_photo_gallery_recycler_view);
        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        setupAdapter(mItems);
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

                if(query == null || isSearching){
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
                    item = gson.fromJson(response.toString(), GalleryItem.class);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });
    }

    private void setupAdapter(List<GalleryItem> mItems){
        mPhotoRecyclerView.setAdapter(new PhotoAdapter(mItems));
    }

    /* Recyclerview setup */
    private class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mItemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mItemImageView = (ImageView) itemView
                    .findViewById(R.id.fragment_photo_gallery_image_view);
        }

        public void bindGalleryItem(GalleryItem galleryItem) {
            Picasso.with(getActivity())
                    .load(galleryItem.getUrl_s())
                    .into(mItemImageView);
        }

        @Override
        public void onClick(View v) {
            GalleryItem currentItem = mItems.get(getAdapterPosition());
            Log.d(TAG, mItems.get(getAdapterPosition()).getId());
            //sendRequest(currentItem.getId(), false);
           // Intent intent = PhotoSingleActivity.createIntent(getActivity(), mItems.get(getAdapterPosition()));

            Intent intent = PhotoSingleActivity.createIntent(getActivity(), mItems, currentItem.getId());
            startActivity(intent);
        }
    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private List<GalleryItem> mGalleryItems;

        public PhotoAdapter(List<GalleryItem> galleryItems) {
            mGalleryItems = galleryItems;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.gallery_item, viewGroup, false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(PhotoHolder photoHolder, int position) {
            GalleryItem galleryItem = mGalleryItems.get(position);
            photoHolder.bindGalleryItem(galleryItem);
        }

        @Override
        public int getItemCount() {
            return mGalleryItems.size();
        }
    }
}
