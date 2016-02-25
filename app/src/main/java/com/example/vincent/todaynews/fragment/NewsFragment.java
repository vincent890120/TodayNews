package com.example.vincent.todaynews.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.activity.CityListActivity;
import com.example.vincent.todaynews.activity.DetailsActivity;
import com.example.vincent.todaynews.adapter.NewsAdapter;
import com.example.vincent.todaynews.adapter.NewsListViewAdapter;
import com.example.vincent.todaynews.bean.NewsEntity;
import com.example.vincent.todaynews.mock.Constants;
import com.example.vincent.todaynews.widget.MesasuredListView;
import com.example.vincent.todaynews.widget.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView mNewsListView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();

    private int columnType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        columnType = getArguments().getInt("column_type", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_news, null);
        if (columnType == 2) {
            LinearLayout checkCity = (LinearLayout) view.findViewById(R.id.check_city);
            checkCity.setVisibility(View.VISIBLE);
            checkCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), CityListActivity.class);
                    startActivity(intent);
                }
            });
        }
        mNewsListView = (PullToRefreshListView) view.findViewById(R.id.new_listview);
        mNewsAdapter = new NewsAdapter(getActivity(), newsList);

        mNewsListView.setAdapter(mNewsAdapter);
        mNewsListView.setOnItemClickListener(this);

        mNewsListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshListView listView) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mNewsListView.onRefreshComplete();
                        newsList.clear();
                        newsList = Constants.getNewsListData();
                        mNewsAdapter.resetList(newsList);
                        mNewsAdapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsList.clear();
                newsList = Constants.getNewsListData();
                mNewsAdapter.resetList(newsList);
                mNewsAdapter.notifyDataSetChanged();
            }
        }, 2000);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("news", newsList.get(position));
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
