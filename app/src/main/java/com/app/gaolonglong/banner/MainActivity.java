package com.app.gaolonglong.banner;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.gaolonglong.banner.model.News;
import com.app.gaolonglong.banner.model.Story;
import com.app.gaolonglong.banner.util.Utils;
import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String ZHIHU_API = "http://news-at.zhihu.com/api/4/news/latest";
    private ViewPager mViewPager;
    private BannerAdapter bannerAdapter;
    private TextView mTitle;
    private ImageView[] mIndicator;

    private Gson gson;
    private News news;
    private Timer mTimer = new Timer();

    private int mBannerPosition = 0;
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 5;
    private boolean mIsUserTouched = false;

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!mIsUserTouched){
                mBannerPosition = (mBannerPosition + 1) % FAKE_BANNER_SIZE;
                /**
                 * Android在子线程更新UI的几种方法
                 * Handler，AsyncTask,view.post,runOnUiThread
                 */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mBannerPosition == FAKE_BANNER_SIZE - 1){
                            mViewPager.setCurrentItem(DEFAULT_BANNER_SIZE - 1,false);
                        }else {
                            mViewPager.setCurrentItem(mBannerPosition);
                        }
                    }
                });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();

    }

    private void init() {

        initView();

        //第一个5000表示从调用schedule()方法到第一次执行mTimerTask的run()方法的时间间隔
        //第二个5000表示以后每隔5000毫秒执行一次mTimerTask的run()方法
        mTimer.schedule(mTimerTask,5000,5000);

        initEvent();
    }

    private void initEvent() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBannerPosition = position;
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        //遍历mIndicator重置src为normal
        for (ImageView indicator : mIndicator){
            indicator.setImageResource(R.drawable.dot_normal);
        }
        mIndicator[position].setImageResource(R.drawable.dot_focused);
        mTitle.setText(news.getTop_stories().get(position).getTitle());
    }

    private void initView() {
        //dot
        mIndicator = new ImageView[]{
                (ImageView) findViewById(R.id.dot_indicator1),
                (ImageView) findViewById(R.id.dot_indicator2),
                (ImageView) findViewById(R.id.dot_indicator3),
                (ImageView) findViewById(R.id.dot_indicator4),
                (ImageView) findViewById(R.id.dot_indicator5),
        };
        //view
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTitle = (TextView) findViewById(R.id.title);
        //Gson
        gson = new Gson();
        //loadData
        loadData();
        //Touch
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE){
                    mIsUserTouched = true;
                }else if (action == MotionEvent.ACTION_UP){
                    mIsUserTouched = false;
                }
                return false;
            }
        });

    }

    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ZHIHU_API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dealWithResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        //给stringRequest设置Tag，便于在Activity销毁时cancel掉网络请求
        stringRequest.setTag(this.getLocalClassName());
        BannerApplication.mInstance.getRequestQueue().add(stringRequest);
    }

    private void dealWithResponse(String response) {
        news = gson.fromJson(response,News.class);
        bannerAdapter = new BannerAdapter(this,news.getTop_stories());
        mViewPager.setAdapter(bannerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //cancel掉mTimer和网络请求
        mTimer.cancel();
        BannerApplication.mInstance.getRequestQueue().cancelAll(this.getLocalClassName());
    }

    private class BannerAdapter extends PagerAdapter{

        private Context context;
        private List<Story> newsList;

        public BannerAdapter(Context context, List<Story> newsList) {
            this.context = context;
            this.newsList = newsList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= DEFAULT_BANNER_SIZE;

            View view = LayoutInflater.from(context).inflate(R.layout.item, container, false);
            ImageView image = (ImageView) view.findViewById(R.id.image);

            Utils.loadImage(newsList.get(position).getImage(), image);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,pos+"-->"+newsList.get(pos).getTitle(),Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return FAKE_BANNER_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            //这个有点懵逼..
            int position = mViewPager.getCurrentItem();
            if (position == 0){
                position = DEFAULT_BANNER_SIZE;
                mViewPager.setCurrentItem(position,false);
            }else if (position == FAKE_BANNER_SIZE - 1){
                position = DEFAULT_BANNER_SIZE - 1;
                mViewPager.setCurrentItem(position,false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
