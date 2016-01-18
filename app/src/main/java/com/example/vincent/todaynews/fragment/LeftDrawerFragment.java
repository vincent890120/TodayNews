package com.example.vincent.todaynews.fragment;


import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vincent.todaynews.Mock.Constants;
import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.bean.LeftItemClassify;

import java.util.ArrayList;

/**
 * Created by vincent on 16/1/6.
 */
public class LeftDrawerFragment extends Fragment {
    private DrawerLayout leftDrawerLayout;
    private ListView mListView;

    private LeftDrawerListAdapter leftDrawerListAdapter;
    private ArrayList<LeftItemClassify> leftItemClassifies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_drawer, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.left_listview);
        leftItemClassifies = new ArrayList<>();
        leftItemClassifies = Constants.getLeftCategoryData();
        leftDrawerListAdapter = new LeftDrawerListAdapter();
        mListView.setAdapter(leftDrawerListAdapter);
    }

    public void setDrawerLayout(DrawerLayout drawer_layout) {
        this.leftDrawerLayout = drawer_layout;
    }

    public class LeftDrawerListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return leftItemClassifies.size();
        }

        @Override
        public LeftItemClassify getItem(int position) {
            return leftItemClassifies.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_left_list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.left_list_item_icon);
                viewHolder.text = (TextView) convertView.findViewById(R.id.left_list_item_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.icon.setBackgroundResource(getItem(position).getIconId());
            viewHolder.text.setText(getItem(position).getText());
            return convertView;
        }

        public class ViewHolder {
            private ImageView icon;
            private TextView text;
        }
    }
}
