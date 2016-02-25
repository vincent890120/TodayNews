package com.example.vincent.todaynews.utils;

/**
 * Created by vincent on 16/2/2.
 */
import android.text.TextUtils;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class NewsDetailsService {
    public static String getNewsDetails(String url, String news_title,
                                        String news_date) {
        Document document = null;
        String data = "<body>" +
                "<center><h2 style='font-size:16px;'>" + news_title + "</h2></center>";
        data = data + "<p align='left' style='margin-left:10px'>"
                + "<span style='font-size:10px;'>"
                + news_date
                + "</span>"
                + "</p>";
        data = data + "<hr size='1' />";
        try {
            document = Jsoup.connect(url).timeout(9000).get();
            Element element = null;
            if (TextUtils.isEmpty(url)) {
                data = "";
                element = document.getElementById("memberArea");
            } else {
                element = document.select("div.article-content").first();
            }
            if (element != null) {
                data = data + element.toString();
            }
            data = data + "</body>";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static ArrayList<String> getImage(Element element){
        ArrayList<String> imageList = new ArrayList<>();
        Elements imgElements = element.select("p img");
        for(Element imgElement:imgElements){
            String img = imgElement.attr("src");
            imageList.add(img);
        }
        return imageList;
    }
}
