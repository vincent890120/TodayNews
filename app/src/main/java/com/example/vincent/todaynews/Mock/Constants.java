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
                news.setNewsCategory("荐");
                news.setTitle("惊！2016年上海牌照将会有这些大变化？");
                String url1 = "http://p2.pstatp.com/large/ce00098c2fb39c21eb";
                String url2 = "http://p2.pstatp.com/large/1090001fea54b0a665c";
                String url3 = "http://p3.pstatp.com/large/1090001fea370272597";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                url_list.add(url2);
                url_list.add(url3);
                news.setPicList(url_list);
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6245065030443483394/");
                news.setSource("LICENCE您的汽车专家 ");
                news.setCommentNum(232);
                news.setPublishTime(Long.valueOf(3));
                newsList.add(news);
            } else if (i % 4 == 1) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("图片");
                news.setTitle("为何中国领土上竟然还驻扎着一支外国军队？真相让人恍然大悟");
                String url1 = "http://p3.pstatp.com/large/cd0007ccafb0a0b9d2";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                news.setPicList(url_list);
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6244300794834026753/");
                news.setSource("图说军事 ");
                news.setCommentNum(442);
                news.setPublishTime(Long.valueOf(5));
                newsList.add(news);
            } else if (i % 4 == 2) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("热");
                news.setTitle("科比复出砍23+8 湖人不敌黄蜂");
                String url1 = "http://p2.pstatp.com/large//113001210943df89dd7";
                List<String> url_list = new ArrayList<String>();
                url_list.add(url1);
                news.setPicList(url_list);
                news.setType(2);
                news.setSource_url("http://toutiao.com/a6246180768842777089/");
                news.setSource("虎扑体育");
                news.setCommentNum(44342);
                news.setPublishTime(Long.valueOf(2));
                newsList.add(news);
            } else if (i % 4 == 3) {
                NewsEntity news = new NewsEntity();
                news.setNewsCategory("热");
                news.setTitle("汤普森再爆发 格林三双 勇士擒尼克斯豪取7连胜");
                news.setType(1);
                news.setSource_url("http://toutiao.com/a6246180768842777089/");
                news.setSource("虎扑体育");
                news.setCommentNum(312);
                news.setPublishTime(Long.valueOf(2));
                newsList.add(news);
            }
        }
        return newsList;

    }
}
