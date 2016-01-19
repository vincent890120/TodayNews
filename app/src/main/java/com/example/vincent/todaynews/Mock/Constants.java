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
        int[] iconIds = { R.drawable.sellicon_leftdrawer, R.drawable.feedbackicon_leftdrawer,R.drawable.dynamicicon_leftdrawer, R.drawable.topicicon_leftdrawer, R.drawable.favoriteicon_leftdrawer, R.drawable.activityicon_leftdrawer, R.drawable.sellicon_leftdrawer, R.drawable.feedbackicon_leftdrawer};
        String[] texts = {"我要爆料", "今日游戏", "好友动态", "我的话题", "收藏", "活动", "商城", "反馈"};
        for (int i = 0; i < iconIds.length; i++) {
            LeftItemClassify leftItemClassify = new LeftItemClassify(iconIds[i], texts[i]);
            leftItemClassifies.add(leftItemClassify);
        }
        return leftItemClassifies;
    }
}
