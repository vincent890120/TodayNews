package com.example.vincent.todaynews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vincent.todaynews.R;
import com.example.vincent.todaynews.bean.NewsEntity;
import com.example.vincent.todaynews.tools.BaseTools;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by vincent on 16/1/19.
 */
public class NewsListViewAdapter extends BaseAdapter {

    ArrayList<NewsEntity> newsList = new ArrayList<NewsEntity>();
    LayoutInflater layoutInflater;
    Context mContext;

    private final static int TYPE_COUNT = 3;
    private final static int TYPE_BOTTOMVIEW = 1;
    private final static int TYPE_RIGHTVIEW = 2;

    public NewsListViewAdapter(ArrayList<NewsEntity> newsList, Context context) {
        this.newsList = newsList;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public NewsEntity getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position).getType() == 1) {
            return TYPE_BOTTOMVIEW;
        } else if (newsList.get(position).getType() == 2) {
            return TYPE_RIGHTVIEW;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        NewsBottomViewHolder newsBottomViewHolder = null;
        NewsRightViewHolder newsRightViewHolder = null;
        if (convertView == null) {
            switch (type) {
                case TYPE_BOTTOMVIEW: {
                    newsBottomViewHolder = new NewsBottomViewHolder();
                    convertView = layoutInflater.inflate(R.layout.newsfragment_list_item_bottomview, parent, false);
                    newsBottomViewHolder.item_bottomview_title = (TextView) convertView.findViewById(R.id.item_bottomview_title);
                    newsBottomViewHolder.item_bottomview_imageview = (LinearLayout) convertView.findViewById(R.id.item_bottomview_imageview);
                    newsBottomViewHolder.item_bottomview_label = (TextView) convertView.findViewById(R.id.item_bottomview_label);
                    newsBottomViewHolder.item_bottomview_source = (TextView) convertView.findViewById(R.id.item_bottomview_source);
                    newsBottomViewHolder.item_bottomview_comment = (TextView) convertView.findViewById(R.id.item_bottomview_comment);
                    newsBottomViewHolder.item_bottomview_time = (TextView) convertView.findViewById(R.id.item_bottomview_time);
                    convertView.setTag(R.id.Tag_BOTTOMVIEW, newsBottomViewHolder);
                    break;
                }
                case TYPE_RIGHTVIEW: {
                    newsRightViewHolder = new NewsRightViewHolder();
                    convertView = layoutInflater.inflate(R.layout.newsfragment_list_item_rightview, parent, false);
                    newsRightViewHolder.item_rightview_title = (TextView) convertView.findViewById(R.id.item_rightview_title);
                    newsRightViewHolder.item_rightview_imageview = (ImageView) convertView.findViewById(R.id.item_rightview_imageview);
                    newsRightViewHolder.item_rightview_label = (TextView) convertView.findViewById(R.id.item_rightview_label);
                    newsRightViewHolder.item_rightview_source = (TextView) convertView.findViewById(R.id.item_rightview_source);
                    newsRightViewHolder.item_rightview_comment = (TextView) convertView.findViewById(R.id.item_rightview_comment);
                    newsRightViewHolder.item_rightview_time = (TextView) convertView.findViewById(R.id.item_rightview_time);
                    convertView.setTag(R.id.Tag_RIGHTVIEW, newsRightViewHolder);
                    break;
                }
                default:
                    break;
            }
        } else {
            switch (type) {
                case TYPE_BOTTOMVIEW: {
                    newsBottomViewHolder = (NewsBottomViewHolder) convertView.getTag(R.id.Tag_BOTTOMVIEW);
                    break;
                }
                case TYPE_RIGHTVIEW: {
                    newsRightViewHolder = (NewsRightViewHolder) convertView.getTag(R.id.Tag_RIGHTVIEW);
                    break;
                }
                default:
                    break;
            }
        }
        NewsEntity newsEntity = getItem(position);
        switch (type) {
            case TYPE_BOTTOMVIEW: {
                newsBottomViewHolder.item_bottomview_title.setText(newsEntity.getTitle());
                newsBottomViewHolder.item_bottomview_imageview.removeAllViews();
                if (newsEntity.getPicList() != null) {
                    int count = newsEntity.getPicList().size();
                    int itemWidth = BaseTools.getWidthForImage(mContext, count);
                    for (int i = 0; i < count; i++) {
                        ImageView imageView = new ImageView(mContext);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(itemWidth, count == 1 ? (int) (itemWidth * 0.6) : (int) (itemWidth * 0.8));
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setPadding(8, 8, 8, 8);
                        ImageLoader.getInstance().displayImage(newsEntity.getPicList().get(i), imageView);
                        newsBottomViewHolder.item_bottomview_imageview.addView(imageView);
                    }
                }
                newsBottomViewHolder.item_bottomview_label.setText(newsEntity.getNewsCategory());
                newsBottomViewHolder.item_bottomview_source.setText(newsEntity.getSource());
                newsBottomViewHolder.item_bottomview_comment.setText(newsEntity.getCommentNum() + "评论");
                newsBottomViewHolder.item_bottomview_time.setText(newsEntity.getPublishTime() + "小时");
                break;
            }
            case TYPE_RIGHTVIEW: {
                newsRightViewHolder.item_rightview_title.setText(newsEntity.getTitle());
                int itemWidth = BaseTools.getWidthForImage(mContext, 3);
                newsRightViewHolder.item_rightview_imageview.setLayoutParams(new LinearLayout.LayoutParams(itemWidth, (int) (itemWidth * 0.6)));
                ImageLoader.getInstance().displayImage(newsEntity.getPicList().get(0), newsRightViewHolder.item_rightview_imageview);
                newsRightViewHolder.item_rightview_label.setText(newsEntity.getNewsCategory());
                newsRightViewHolder.item_rightview_source.setText(newsEntity.getSource());
                newsRightViewHolder.item_rightview_comment.setText(newsEntity.getCommentNum() + "评论");
                newsRightViewHolder.item_rightview_time.setText(newsEntity.getPublishTime() + "小时");
                break;
            }
            default:
                break;

        }

        return convertView;
    }

    public class NewsBottomViewHolder {
        //标题
        public TextView item_bottomview_title;
        //图片源
        public LinearLayout item_bottomview_imageview;
        //标签
        public TextView item_bottomview_label;
        //消息来源
        public TextView item_bottomview_source;
        //评论数量
        public TextView item_bottomview_comment;
        //发布时间
        public TextView item_bottomview_time;
    }

    public class NewsRightViewHolder {
        //标题
        public TextView item_rightview_title;
        //图片源
        public ImageView item_rightview_imageview;
        //标签
        public TextView item_rightview_label;
        //消息来源
        public TextView item_rightview_source;
        //评论数量
        public TextView item_rightview_comment;
        //发布时间
        public TextView item_rightview_time;
    }
}
