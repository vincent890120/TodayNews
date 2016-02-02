package com.example.vincent.todaynews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.activity.DetailsActivity;
import com.example.vincent.todaynews.adapter.NewsListViewAdapter;
import com.example.vincent.todaynews.bean.NewsEntity;
import com.example.vincent.todaynews.mock.Constants;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView mNewsListView;
    private NewsListViewAdapter mNewsListViewAdapter;
    private ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsList = Constants.getNewsListData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
        mNewsListView = (ListView) view.findViewById(R.id.new_listview);
        mNewsListViewAdapter = new NewsListViewAdapter(newsList, getActivity());
        mNewsListView.setAdapter(mNewsListViewAdapter);
        mNewsListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
