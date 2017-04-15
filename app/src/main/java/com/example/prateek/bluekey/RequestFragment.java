package com.example.prateek.bluekey;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RequestFragment extends Fragment {

    private List<Request> requestList;
    private RequestAdapter requestAdapter;
    private RecyclerView recyclerView;



    public RequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        requestList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_request);
        requestAdapter = new RequestAdapter(requestList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);
        prepareRequestData();
        return view;
    }

    public void prepareRequestData() {
        Request request = new Request("Prateek", "user_icon", "16/02/2017 12:29:00", "9829582587");
        requestList.add(request);

        request = new Request("Neeraj", "user_icon", "16/02/2017 12:30:00", "9582555293");
        requestList.add(request);

        request = new Request("Alok", "user_icon", "16/02/2017 12:31:00" , "9680129033");
        requestList.add(request);

        requestAdapter.notifyDataSetChanged();
    }

}
