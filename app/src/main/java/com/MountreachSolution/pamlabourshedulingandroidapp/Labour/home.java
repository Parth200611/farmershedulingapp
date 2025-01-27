package com.MountreachSolution.pamlabourshedulingandroidapp.Labour;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.MountreachSolution.pamlabourshedulingandroidapp.R;
import com.MountreachSolution.pamlabourshedulingandroidapp.SQLiteDataBase.FarmerWorkDatabase;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {



        private RecyclerView recyclerView;
        private SearchView searchView;
        private WorkPostingAdapter adapter;
    private ProgressBar progressBar; // Progress bar
       ArrayList<String> id,name,mobileno,address,work,wages,starttime,endtime,workdate;
        private FarmerWorkDatabase dbHelper;
        private int pageLimit = 20;  // Number of items to load per page
        private int offset = 0;  // Offset for pagination
    private boolean isToastShown = false; // Flag to track toast visibility


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home2, container, false);
            setRetainInstance(true); // Retain instance to preserve data
            setHasOptionsMenu(true); // Enable options menu

            recyclerView = view.findViewById(R.id.recyclerView);
            searchView = view.findViewById(R.id.searchView);
            progressBar = view.findViewById(R.id.progressBar); // Initialize progress bar

            dbHelper = new FarmerWorkDatabase(requireContext());
            id=new ArrayList<>();
            name=new ArrayList<>();
            mobileno=new ArrayList<>();
            address=new ArrayList<>();
            work=new ArrayList<>();
            wages=new ArrayList<>();
            starttime=new ArrayList<>();
            endtime=new ArrayList<>();
            workdate=new ArrayList<>();

            adapter=new WorkPostingAdapter(getActivity(),id,name,mobileno,address,work,wages,starttime,endtime,workdate);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapter.getFilter().filter(query);  // Filter data
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);  // Update filter on text change
                    return false;
                }
            });


            // Initial data load
            displaydata();
            // Handle search query input
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    filter(query);
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    filter(newText);
//                    return false;
//                }
//            });false

            return view;
        }

    private void displaydata() {
        if (!isToastShown) { // Show toast only if it hasn't been shown yet
            Toast.makeText(getActivity(), "NetWork Low Reload page", Toast.LENGTH_SHORT).show();
            isToastShown = true; // Set the flag to true
        }
            Cursor cursor=dbHelper.getdata();
            if (cursor.getCount()==0){
                Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
            }else{
                while(cursor.moveToNext()){
                    id.add(cursor.getString(0));
                    name.add(cursor.getString(1));
                    mobileno.add(cursor.getString(2));
                    address.add(cursor.getString(3));
                    work.add(cursor.getString(4));
                    wages.add(cursor.getString(5));
                    starttime.add(cursor.getString(6));
                    endtime.add(cursor.getString(7));
                    workdate.add(cursor.getString(8));
                }
            }
    }


    }