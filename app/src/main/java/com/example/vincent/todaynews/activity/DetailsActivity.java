package com.example.vincent.todaynews.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.base.BaseActivity;
import com.example.vincent.todaynews.bean.NewsEntity;
import com.example.vincent.todaynews.tools.DateTools;
import com.example.vincent.todaynews.tools.NewsDetailsService;

import java.util.ArrayList;

/**
 * Created by vincent on 16/2/1.
 */
public class DetailsActivity extends BaseActivity {

    private ProgressBar progressBar;
    private WebView webView;
    private NewsEntity newsEntity;
    private String news_url;
    private String news_title;
    private String news_source;
    private String news_date;
    private TextView title;
    private FrameLayout customview_layout;
    private TextView action_comment_count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);
        getData();
        findViewById();
        initView();
    }

    @Override
    protected void findViewById() {
        webView = (WebView)findViewById(R.id.wb_details);
        title = (TextView) findViewById(R.id.title);
        progressBar = (ProgressBar) findViewById(R.id.ss_htmlprogessbar);
        customview_layout = (FrameLayout) findViewById(R.id.customview_layout);
        //底部栏目
        action_comment_count = (TextView) findViewById(R.id.action_comment_count);

        progressBar.setVisibility(View.VISIBLE);
        title.setTextSize(13);
        title.setVisibility(View.VISIBLE);
        title.setText(news_url);
        action_comment_count.setText(String.valueOf(newsEntity.getCommentNum()));
    }

    @Override
    protected void initView() {
       initWebView();
    }
    private void getData(){
        newsEntity = (NewsEntity)getIntent().getSerializableExtra("news");
        news_url = newsEntity.getSource_url();
        news_title = newsEntity.getTitle();
        news_source = newsEntity.getSource();
        news_date = DateTools.getNewsDetailsDate(String.valueOf(newsEntity.getPublishTime()));
    }

    private void initWebView(){
       LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        if (!TextUtils.isEmpty(news_url)) {
            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);//设置可以运行JS脚本
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
            settings.setSupportZoom(false);// 用于设置webview放大
            settings.setBuiltInZoomControls(false);
            webView.setBackgroundResource(R.color.transparent);
            // 添加js交互接口类，并起别名 imagelistner
            webView.addJavascriptInterface(new JavascriptImageInterface(getApplicationContext()),"imagelistner");
            webView.setWebChromeClient(new MyWebChromeClient());
            webView.setWebViewClient(new MyWebViewClient());
            new MyAsnycTask().execute(news_url, news_title, news_source + " " + news_date);
        }
    }

    private class MyAsnycTask extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... urls) {
            String data= NewsDetailsService.getNewsDetails(urls[0], urls[1], urls[2]);
            return data;
        }

        @Override
        protected void onPostExecute(String data) {
            webView.loadDataWithBaseURL (null, data, "text/html", "utf-8",null);
        }
    }


    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\");"
                + "var imgurl=''; " + "for(var i=0;i<objs.length;i++)  " + "{"
                + "imgurl+=objs[i].src+',';"
                + "    objs[i].onclick=function()  " + "    {  "
                + "        window.imagelistner.openImage(imgurl);  "
                + "    }  " + "}" + "})()");
    }

    // js通信接口
    public class JavascriptImageInterface {

        private Context context;

        public JavascriptImageInterface(Context context) {
            this.context = context;
        }
        @JavascriptInterface
        public void openImage(String img) {

            String[] imgs = img.split(",");
            ArrayList<String> imgsUrl = new ArrayList<String>();
            for (String s : imgs) {
                imgsUrl.add(s);
            }
            Intent intent = new Intent();
            intent.putStringArrayListExtra("infos", imgsUrl);
            intent.setClass(context, ImageShowActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }


    // 监听
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
            progressBar.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            progressBar.setVisibility(View.GONE);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            // TODO Auto-generated method stub
            if(newProgress != 100){
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
