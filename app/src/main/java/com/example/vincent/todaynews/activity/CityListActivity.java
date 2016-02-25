package com.example.vincent.todaynews.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.adapter.MergeAdapter;
import com.example.vincent.todaynews.base.BaseActivity;
import com.example.vincent.todaynews.bean.NewsClassify;
import com.example.vincent.todaynews.bean.NewsEntity;
import com.example.vincent.todaynews.mock.Constants;
import com.example.vincent.todaynews.model.City;
import com.example.vincent.todaynews.widget.AlphabetBar;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by vincent on 16/2/23.
 */
public class CityListActivity extends BaseActivity implements View.OnClickListener, TextWatcher, AdapterView.OnItemClickListener {

    private TextView gpscitynameTextView;
    private EditText searchEditText;
    private TextView curChar;
    private ListView listView;
    private AlphabetBar indexBar;

    protected SectionIndexerMergeAdapter mergeAdapter;
    protected CityAdapter mAdapter;

    protected ArrayList<City> cities, allCities;

    private static final int listIndexId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        initView();
        allCities = new ArrayList<>();
        searchEditText.addTextChangedListener(this);
        mergeAdapter = new SectionIndexerMergeAdapter();
        getCityList();
    }

    @Override
    protected void initView() {
        gpscitynameTextView = (TextView) CityListActivity.this.findViewById(R.id.gpsCity);
        searchEditText = (EditText) CityListActivity.this.findViewById(R.id.start_search);
        curChar = (TextView) CityListActivity.this.findViewById(R.id.currentChar);
        listView = (ListView) CityListActivity.this.findViewById(R.id.list);
        indexBar = (AlphabetBar) CityListActivity.this.findViewById(R.id.sideBar);
        CityListActivity.this.findViewById(R.id.gpsCityLayout).setOnClickListener(this);
        ((ListView) CityListActivity.this.findViewById(R.id.list)).setOnItemClickListener(this);
    }

    //View.OnClickListener
    @Override
    public void onClick(View v) {

    }

    //AdapterView.OnItemClickListener
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<NewsClassify> newsClassifyList = new ArrayList<>();
        for (NewsClassify newsClassify : Constants.getNewsData()) {
            if (newsClassify.getType() == 2) {
                newsClassify.setTitle(allCities.get(position).getName());
            }
            newsClassifyList.add(newsClassify);
        }
        EventBus.getDefault().post(newsClassifyList);
        finish();
    }

    //TextWatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private void getCityList() {
        List<City> cities = Constants.getCityList();
        for (int i = (int) 'A'; i <= (int) 'Z'; i++) {
            allCities.add(new City(listIndexId, (char) i + "", i));
            for (int j = 0; j < cities.size(); j++) {
                City aCity = cities.get(j);
                if (aCity != null) {
                    allCities.add(aCity);
                }
            }
        }
        setAdapterAllCities();
    }

    private void setAdapterAllCities() {
        cities = allCities;
        mAdapter = new CityAdapter(this);
        mergeAdapter = new SectionIndexerMergeAdapter();
        mergeAdapter.addAdapter(mAdapter);
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new OnListScrollListener());
        indexBar.setListView(listView);
        indexBar.setSectionIndexter(mergeAdapter);
        indexBar.setVisibility(View.VISIBLE);
        indexBar.setOnSelectedListener(new OnListSelectedListener());
    }

    class SectionIndexerMergeAdapter extends MergeAdapter implements SectionIndexer {
        protected final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        @Override
        public int getPositionForSection(int section) {

            if ((section <= 0) || (section >= SECTIONS.length)) {
                return 0;
            }
            String str = SECTIONS[section];
            if (cities != null) {
                for (int i = 0; i < cities.size(); ++i) {
                    if (cities.get(i).firstChar().equals(str)) {
                        return i;
                    }
                }
            }
            return -1;
        }

        @Override
        public int getSectionForPosition(int position) {

            if ((position < 0) || (position >= cities.size())) {
                return 0;
            }
            String str = cities.get(position).firstChar();
            for (int i = 0; i < SECTIONS.length; ++i) {
                if (SECTIONS[i].equals(str)) {
                    return i;
                }
            }
            return 0;
        }

        @Override
        public Object[] getSections() {
            return SECTIONS;
        }
    }

    protected class CityAdapter extends ArrayAdapter<City> {

        public CityAdapter(Context context) {
            super(context, R.layout.activity_citylist_item, R.id.title);
            if (cities != null) {
                for (City city : cities) {
                    add(city);
                }
            }
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            BasicViewHolder viewHolder;
            City obj = (City) getItem(position);
            if (obj != null) {
                if (obj.getId() == listIndexId) {
                    convertView = getLayoutInflater().inflate(R.layout.activity_citylist_item_index, parent, false);
                } else {
                    convertView = getLayoutInflater().inflate(R.layout.activity_citylist_item, parent, false);
                }
                viewHolder = new BasicViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.title);
                String typeName = obj.getName();
                viewHolder.textView.setText(typeName);
            }
            return convertView;
        }

        public class BasicViewHolder {
            public TextView textView;
        }
    }

    protected class OnListScrollListener implements AbsListView.OnScrollListener {
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {
            int pos = view.getFirstVisiblePosition();
            showSelectedChar(pos);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case SCROLL_STATE_IDLE:
                    curChar.setVisibility(View.INVISIBLE);
                    break;
                case SCROLL_STATE_TOUCH_SCROLL:
                    curChar.setVisibility(View.VISIBLE);
                    break;
                default:
            }
        }

    }

    protected class OnListSelectedListener implements AlphabetBar.OnSelectedListener {
        @Override
        public void onSelected(int pos) {
            curChar.setVisibility(View.VISIBLE);
            showSelectedChar(pos);
        }

        @Override
        public void onUnselected() {
            CityListActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    curChar.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    protected void showSelectedChar(int pos) {
        Object[] sections = mergeAdapter.getSections();
        int i = mergeAdapter.getSectionForPosition(pos);
        if ((i < 0) || (sections == null) || (i >= sections.length)) {
            return;
        }
        curChar.setText(sections[i].toString());
    }
}
