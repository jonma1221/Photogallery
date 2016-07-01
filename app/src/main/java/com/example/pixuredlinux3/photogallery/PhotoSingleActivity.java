package com.example.pixuredlinux3.photogallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import Fragments.PhotoSingleFragment;
import model.GalleryItem;

/**
 * Created by pixuredlinux3 on 6/30/16.
 */
public class PhotoSingleActivity extends AppCompatActivity{

    private final static String ID = "id";
    private final static String GALLERY_LIST = "list";

    private ViewPager mViewPager;
    private List<GalleryItem> items;
    String currentItemID;
    /*@Override
    protected Fragment createFragment() {
        GalleryItem item = getIntent().getParcelableExtra("item");
        return PhotoSingleFragment.newInstance(item);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_search_results);

        currentItemID = getIntent().getStringExtra(ID);
        Log.d("Activity", currentItemID);
        items = getIntent().getParcelableArrayListExtra(GALLERY_LIST);

        mViewPager = (ViewPager)findViewById(R.id.search_result_view_pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                GalleryItem galleryItem = items.get(position);
                return PhotoSingleFragment.newInstance(galleryItem);
            }

            @Override
            public void notifyDataSetChanged() {
                super.notifyDataSetChanged();
            }

            @Override
            public int getCount() {
                return items.size();
            }
        });

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(currentItemID)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public static Intent createIntent(Context context, List<GalleryItem> item, String id){
        Intent intent = new Intent(context, PhotoSingleActivity.class);
        intent.putParcelableArrayListExtra(GALLERY_LIST, (ArrayList) item);
        intent.putExtra(ID, id);
        return intent;
    }
}
