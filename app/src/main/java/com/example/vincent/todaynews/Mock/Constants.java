package com.example.vincent.todaynews.mock;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.bean.LeftItemClassify;
import com.example.vincent.todaynews.bean.NewsClassify;
import com.example.vincent.todaynews.bean.NewsEntity;

import java.util.ArrayList;
import java.util.List;


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
        int[] iconIds = {R.drawable.sellicon_leftdrawer, R.drawable.feedbackicon_leftdrawer, R.drawable.dynamicicon_leftdrawer, R.drawable.topicicon_leftdrawer, R.drawable.favoriteicon_leftdrawer, R.drawable.activityicon_leftdrawer, R.drawable.sellicon_leftdrawer, R.drawable.feedbackicon_leftdrawer};
        String[] texts = {"我要爆料", "今日游戏", "好友动态", "我的话题", "收藏", "活动", "商城", "反馈"};
        for (int i = 0; i < iconIds.length; i++) {
            LeftItemClassify leftItemClassify = new LeftItemClassify(iconIds[i], texts[i]);
            leftItemClassifies.add(leftItemClassify);
        }
        return leftItemClassifies;
    }

    public static ArrayList<NewsEntity> getNewsListData() {
        ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();
        for (int i = 0; i < 10; i++) {
            if (i % 4 == 0) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("热");
                news.setTitle("看看以前的高圆圆，不得不说女神就是女神");
                String url1 = "http://p1.pstatp.com/large/10a0004e89a939773d7";
                String url2 = "http://p2.pstatp.com/large/1090004e7c4a834e806";
                String url3 = "http://p3.pstatp.com/large/10a0004e898223ae2c2";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                url_list.add(url2);
                url_list.add(url3);
                news.setPicList(url_list);
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6246307657164079361/");
                news.setSource("坐听风起 ");
                news.setCommentNum(232);
                news.setPublishTime(Long.valueOf(3));
                newsList.add(news);
            } else if (i % 4 == 1) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("荐");
                news.setTitle("上课偷玩的诺基亚：那些年塞班上的神级游戏");
                String url1 = "http://p3.pstatp.com/large/12800061c195cde9129";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                news.setPicList(url_list);
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6246310068653359362/");
                news.setSource("IT之家 ");
                news.setCommentNum(442);
                news.setPublishTime(Long.valueOf(5));
                newsList.add(news);
            } else if (i % 4 == 2) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("热");
                news.setTitle("威少三节三双杜兰特28+9 雷霆大胜奇才");
                String url1 = "http://p3.pstatp.com/large//132000485a10d372745";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                news.setPicList(url_list);
                news.setType(2);
                news.setSource_url("http://toutiao.com/a6246531032233804289/");
                news.setSource("虎扑体育");
                news.setCommentNum(44342);
                news.setPublishTime(Long.valueOf(2));
                newsList.add(news);
            } else if (i % 4 == 3) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("热");
                news.setTitle("阿里组织部开年终会 马云盘点2015阿里7大要点");
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6246279913181348097/");
                news.setSource("TechWeb");
                news.setCommentNum(312);
                news.setPublishTime(Long.valueOf(2));
                newsList.add(news);
            }
        }
        return newsList;

    }
}
