package com.example.vincent.todaynews.Mock;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.bean.LeftItemClassify;
import com.example.vincent.todaynews.bean.NewsClassify;

import java.util.ArrayList;


public class Constants {
    public static ArrayList<NewsClassify> getNewsData() {
        ArrayList<NewsClassify> newsClassify = new ArrayList<NewsClassify>();
        NewsClassify classify = new NewsClassify();
        classify.setId(0);
        classify.setTitle("推荐");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(1);
        classify.setTitle("热点");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(2);
        classify.setTitle("数码");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(3);
        classify.setTitle("上海");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(4);
        classify.setTitle("社会");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(5);
        classify.setTitle("娱乐");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(6);
        classify.setTitle("科技");
        newsClassify.add(classify);
        classify = new NewsClassify();
        classify.setId(7);
        classify.setTitle("互联网");
        newsClassify.add(classify);
        return newsClassify;
    }

    public static ArrayList<LeftItemClassify> getLeftCategoryData() {
        ArrayList<LeftItemClassify> leftItemClassifies = new ArrayList<>();
        int[] iconIds = {R.drawable.ic_drawer_search, R.drawable.ic_drawer_favorite, R.drawable.ic_drawer_message,
                R.drawable.ic_drawer_offline, R.drawable.left_drawer_activity_selector, R.drawable.ic_drawer_setting
                , R.drawable.ic_drawer_feedback, R.drawable.ic_drawer_appstore};
        String[] texts = {"搜索", "收藏", "消息", "离线", "活动", "设置", "反馈", "精彩应用"};
        for (int i = 0; i < iconIds.length; i++) {
            LeftItemClassify leftItemClassify = new LeftItemClassify(iconIds[i], texts[i]);
            leftItemClassifies.add(leftItemClassify);
        }
        return leftItemClassifies;
    }
}
