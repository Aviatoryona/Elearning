package com.aviator.elearning.el.frags;


import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aviator.elearning.R;
import com.aviator.elearning.aviator.main.SpaceCraft;
import com.aviator.elearning.el.adapter.RecyclerAdapter;
import com.aviator.elearning.el.models.MyModel;
import com.aviator.elearning.el.net.VolleySingleton;
import com.jpardogo.android.googleprogressbar.library.GoogleProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brucetoo.demo.DisableRecyclerView;
import brucetoo.demo.Utils;
import brucetoo.demo.ViewAnimator;
import brucetoo.videomanage.controller.VideoControllerView;
import brucetoo.videomanage.manager.SingleVideoPlayerManager;
import brucetoo.videomanage.manager.VideoPlayerManager;
import brucetoo.videomanage.meta.CurrentItemMetaData;
import brucetoo.videomanage.meta.MetaData;
import brucetoo.videomanage.ui.MediaPlayerWrapper;
import brucetoo.videomanage.ui.VideoPlayerView;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings("ALL")
public class VideoFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "FavouriteFragment";
    private DisableRecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;

    private FrameLayout mVideoFloatContainer;
    private View mVideoPlayerBg;
    private ImageView mVideoCoverMask;
    private VideoPlayerView mVideoPlayerView;
    private View mVideoLoadingView;
    private ProgressBar mVideoProgressBar;

    private View mCurrentPlayArea;
    private VideoControllerView mCurrentVideoControllerView;
    private int mCurrentActiveVideoItem = -1;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private int mCurrentBuffer;
    private final List<MyModel> modelList = new ArrayList<>();

    public static GoogleProgressBar pg2;

    /**
     * Prevent {@link #stopPlaybackImmediately} be called too many times
     */
    private boolean mCanTriggerStop = true;

    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(null);

    /**
     * Stop video have two scenes
     * 1.click to stop current video and start a new video
     * 2.when video item is dismiss or ViewPager changed ? tab changed ? ...
     */
    private boolean mIsClickToStop;

    private float mOriginalHeight;

    private float mMoveDeltaY;
    private RelativeLayout relativeLayout;
    private View mainView;

    private void startMoveFloatContainer(boolean click) {

        if (mVideoFloatContainer.getVisibility() != View.VISIBLE) return;
        final float moveDelta;

        if (click) {
            Log.e(TAG, "startMoveFloatContainer > mFloatVideoContainer getTranslationY:" + mVideoFloatContainer.getTranslationY());
            ViewAnimator.putOn(mVideoFloatContainer).translationY(0);

            int[] playAreaPos = new int[2];
            int[] floatContainerPos = new int[2];
            mCurrentPlayArea.getLocationOnScreen(playAreaPos);
            mVideoFloatContainer.getLocationOnScreen(floatContainerPos);
            mOriginalHeight = moveDelta = playAreaPos[1] - floatContainerPos[1];

            Log.e(TAG, "startMoveFloatContainer > mFloatVideoContainer playAreaPos[1]:" + playAreaPos[1] + " floatContainerPos[1]:" + floatContainerPos[1]);
        } else {
            moveDelta = mMoveDeltaY;
            Log.e(TAG, "ListView moveDelta :" + moveDelta + "");
        }

        float translationY = moveDelta + (!click ? mOriginalHeight : 0);

        Log.e(TAG, "startMoveFloatContainer > moveDelta:" + moveDelta + " before getTranslationY:" + mVideoFloatContainer.getTranslationY()
                + " mOriginalHeight:" + mOriginalHeight + " translationY:" + translationY);

        ViewAnimator.putOn(mVideoFloatContainer).translationY(translationY);

        Log.i(TAG, "startMoveFloatContainer < after getTranslationY:" + mVideoFloatContainer.getTranslationY());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_videos, container, false);
        requestQueue = VolleySingleton.volleyInstance(getContext());
        return mainView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mainView != null) {
            view = mainView;
            Init(view);
        }
    }

    void Init(View view) {
        mVideoFloatContainer = (FrameLayout) view.findViewById(R.id.layout_float_video_container);
        mVideoPlayerBg = view.findViewById(R.id.video_player_bg);
        mVideoCoverMask = (ImageView) view.findViewById(R.id.video_player_mask);
        mVideoPlayerView = (VideoPlayerView) view.findViewById(R.id.video_player_view);
        mVideoLoadingView = view.findViewById(R.id.video_progress_loading);
        mVideoProgressBar = (ProgressBar) view.findViewById(R.id.video_progress_bar);
        relativeLayout = view.findViewById(R.id.container);
        pg2 = (GoogleProgressBar) view.findViewById(R.id.gProgress2);
        mRecyclerView = (DisableRecyclerView) view.findViewById(R.id.recycler_view);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        Init2();
    }

    void Init2() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnScrollListener(mOnScrollListener);


        mVideoPlayerView.addMediaPlayerListener(new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
            @Override
            public void onVideoSizeChangedMainThread(int width, int height) {

            }

            @Override
            public void onVideoPreparedMainThread() {

                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onVideoPreparedMainThread");
                mVideoFloatContainer.setVisibility(View.VISIBLE);
                mVideoPlayerView.setVisibility(View.VISIBLE);
                mVideoLoadingView.setVisibility(View.VISIBLE);
                //for cover the pre Video frame
                mVideoCoverMask.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVideoCompletionMainThread() {

                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onVideoCompletionMainThread");

                if (mCurrentPlayArea != null) {
                    mCurrentPlayArea.setClickable(true);
                }

                mVideoFloatContainer.setVisibility(View.INVISIBLE);
                mCurrentPlayArea.setVisibility(View.VISIBLE);
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                ViewAnimator.putOn(mVideoFloatContainer).translationY(0);

                //stop update progress
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
            }

            @Override
            public void onErrorMainThread(int what, int extra) {
                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onErrorMainThread");
                if (mCurrentPlayArea != null) {
                    mCurrentPlayArea.setClickable(true);
                    mCurrentPlayArea.setVisibility(View.VISIBLE);
                }
                mVideoFloatContainer.setVisibility(View.INVISIBLE);

                //stop update progress
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
            }

            @Override
            public void onBufferingUpdateMainThread(int percent) {
                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onBufferingUpdateMainThread");
                mCurrentBuffer = percent;
            }

            @Override
            public void onVideoStoppedMainThread() {
                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onVideoStoppedMainThread");
                if (!mIsClickToStop) {
                    mCurrentPlayArea.setClickable(true);
                    mCurrentPlayArea.setVisibility(View.VISIBLE);
                }

                //stop update progress
                mVideoProgressBar.setVisibility(View.GONE);
                mHandler.removeCallbacks(mProgressRunnable);
            }

            @Override
            public void onInfoMainThread(int what) {
                Log.e(MediaPlayerWrapper.VIDEO_TAG, "check play onInfoMainThread what:" + what);
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {

                    //start update progress
                    mVideoProgressBar.setVisibility(View.VISIBLE);
                    mHandler.post(mProgressRunnable);

                    mVideoPlayerView.setVisibility(View.VISIBLE);
                    mVideoLoadingView.setVisibility(View.GONE);
                    mVideoCoverMask.setVisibility(View.GONE);
                    mVideoPlayerBg.setVisibility(View.VISIBLE);
                    createVideoControllerView();

                    mCurrentVideoControllerView.showWithTitle("E-Learning - " + mCurrentActiveVideoItem);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    mVideoLoadingView.setVisibility(View.VISIBLE);
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    mVideoLoadingView.setVisibility(View.GONE);
                }
            }
        });

        paramsMain.put("target", "unknown");
        paramsMain.put("action", "test");

        LoadData(paramsMain);
    }

    private void ToggleMessage(String message) {
//        relativeLayout.setVisibility(View.GONE);
    }

    private void createVideoControllerView() {

        if (mCurrentVideoControllerView != null) {
            mCurrentVideoControllerView.hide();
            mCurrentVideoControllerView = null;
        }

        mCurrentVideoControllerView = new VideoControllerView.Builder(getActivity(), mPlayerControlListener)
                .withVideoTitle("E-Learning")
                .withVideoView(mVideoPlayerView)//to enable toggle display controller view
                .canControlBrightness(true)
                .canControlVolume(true)
                .canSeekVideo(false)
                .exitIcon(R.drawable.video_top_back)
                .pauseIcon(R.drawable.ic_media_pause)
                .playIcon(R.drawable.ic_media_play)
                .shrinkIcon(R.drawable.ic_media_fullscreen_shrink)
                .stretchIcon(R.drawable.ic_media_fullscreen_stretch)
                .build(mVideoFloatContainer);//layout container that hold video play view
    }


    private VideoControllerView.MediaPlayerControlListener mPlayerControlListener = new VideoControllerView.MediaPlayerControlListener() {
        @Override
        public void start() {
            if (checkMediaPlayerInvalid())
                mVideoPlayerView.getMediaPlayer().start();
        }

        @Override
        public void pause() {
            mVideoPlayerView.getMediaPlayer().pause();
        }

        @Override
        public int getDuration() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().getDuration();
            }
            return 0;
        }

        @Override
        public int getCurrentPosition() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().getCurrentPosition();
            }
            return 0;
        }

        @Override
        public void seekTo(int position) {
            if (checkMediaPlayerInvalid()) {
                mVideoPlayerView.getMediaPlayer().seekToPosition(position);
            }
        }

        @Override
        public boolean isPlaying() {
            if (checkMediaPlayerInvalid()) {
                return mVideoPlayerView.getMediaPlayer().isPlaying();
            }
            return false;
        }

        @Override
        public boolean isComplete() {
            return false;
        }

        @Override
        public int getBufferPercentage() {
            return mCurrentBuffer;
        }

        @Override
        public boolean isFullScreen() {
            return getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    || getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        }

        @Override
        public void toggleFullScreen() {
            if (isFullScreen()) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                getActivity().setRequestedOrientation(Build.VERSION.SDK_INT < 9 ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
                        ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            }
        }

        @Override
        public void exit() {
            //TODO to handle exit status
            if (isFullScreen()) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    };

    private boolean checkMediaPlayerInvalid() {
        return mVideoPlayerView != null && mVideoPlayerView.getMediaPlayer() != null;
    }


    RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        int totalDy;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            Log.e(TAG, "onScrollStateChanged state:" + newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                mOriginalHeight = mVideoFloatContainer.getTranslationY();
                totalDy = 0;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            /**
             * NOTE: RecyclerView will callback this method when {@link RecyclerViewFragment#onConfigurationChanged(Configuration)}
             * happened,so handle this special scene.
             */
            if (mPlayerControlListener.isFullScreen()) return;

            //Calculate the total scroll distance of RecyclerView
            totalDy += dy;
            mMoveDeltaY = -totalDy;
            Log.e(TAG, "onScrolled scrollY:" + -totalDy);
            startMoveFloatContainer(false);

            if (mCurrentActiveVideoItem < mLayoutManager.findFirstVisibleItemPosition()
                    || mCurrentActiveVideoItem > mLayoutManager.findLastVisibleItemPosition()) {
                if (mCanTriggerStop) {
                    mCanTriggerStop = false;
                    stopPlaybackImmediately();
                }
            }
        }
    };

    public void stopPlaybackImmediately() {

        mIsClickToStop = false;

        if (mCurrentPlayArea != null) {
            mCurrentPlayArea.setClickable(true);
        }

        if (mVideoPlayerManager != null) {
            Log.e(TAG, "check play stopPlaybackImmediately");
            mVideoFloatContainer.setVisibility(View.INVISIBLE);
            mVideoPlayerManager.stopAnyPlayback();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mVideoFloatContainer == null) return;

        ViewGroup.LayoutParams layoutParams = mVideoFloatContainer.getLayoutParams();

        mCurrentVideoControllerView.hide();

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            //200 indicate the height of video play area
            layoutParams.height = (int) getResources().getDimension(R.dimen.video_item_portrait_height);
            layoutParams.width = Utils.getDeviceWidth(getActivity());

            ViewAnimator.putOn(mVideoFloatContainer).translationY(mOriginalHeight);

            // Show status bar
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            mRecyclerView.setEnableScroll(true);

        } else {

            layoutParams.height = Utils.getDeviceHeight(getActivity());
            layoutParams.width = Utils.getDeviceWidth(getActivity());

            Log.e(TAG, "onConfigurationChanged translationY:" + mVideoFloatContainer.getTranslationY());
            ViewAnimator.putOn(mVideoFloatContainer).translationY(0);

            // Hide status
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

            mRecyclerView.setEnableScroll(false);
        }
        mVideoFloatContainer.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.layout_play_area) {
            mIsClickToStop = true;
            v.setClickable(false);
            if (mCurrentPlayArea != null) {
                if (mCurrentPlayArea != v) {
                    mCurrentPlayArea.setClickable(true);
                    mCurrentPlayArea.setVisibility(View.VISIBLE);
                    mCurrentPlayArea = v;
                } else {//click same area
                    if (mVideoFloatContainer.getVisibility() == View.VISIBLE) return;
                }
            } else {
                mCurrentPlayArea = v;
            }

            //invisible self ,and make visible when video play completely
            v.setVisibility(View.INVISIBLE);
            if (mCurrentVideoControllerView != null)
                mCurrentVideoControllerView.hide();

            mVideoFloatContainer.setVisibility(View.VISIBLE);
            mVideoCoverMask.setVisibility(View.GONE);
            mVideoPlayerBg.setVisibility(View.GONE);

            // VideoModel model = (VideoModel) v.getTag();
            MyModel myModel = (MyModel) v.getTag();
            mCurrentActiveVideoItem = myModel.position;//model.position;
            mCanTriggerStop = true;

            //move container view
            startMoveFloatContainer(true);

            mVideoLoadingView.setVisibility(View.VISIBLE);
            mVideoPlayerView.setVisibility(View.INVISIBLE);

            //play video
            mVideoPlayerManager.playNewVideo(new CurrentItemMetaData(myModel.position, v), mVideoPlayerView, myModel.getVideo_url());//new CurrentItemMetaData(model.position, v), mVideoPlayerView, model.videoUrl
            return;
        }
    }


    /**
     * Runnable for update current video progress
     * 1.start this runnable in {@link MediaPlayerWrapper.MainThreadMediaPlayerListener#onInfoMainThread(int)}
     * 2.stop(remove) this runnable in {@link MediaPlayerWrapper.MainThreadMediaPlayerListener#onVideoStoppedMainThread()}
     * {@link MediaPlayerWrapper.MainThreadMediaPlayerListener#onVideoCompletionMainThread()}
     * {@link MediaPlayerWrapper.MainThreadMediaPlayerListener#onErrorMainThread(int, int)} ()}
     */
    private Runnable mProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mPlayerControlListener != null) {

                if (mCurrentVideoControllerView.isShowing()) {
                    mVideoProgressBar.setVisibility(View.GONE);
                } else {
                    mVideoProgressBar.setVisibility(View.VISIBLE);
                }

                int position = mPlayerControlListener.getCurrentPosition();
                int duration = mPlayerControlListener.getDuration();
                if (duration != 0) {
                    long pos = 1000L * position / duration;
                    int percent = mPlayerControlListener.getBufferPercentage() * 10;
                    mVideoProgressBar.setProgress((int) pos);
                    mVideoProgressBar.setSecondaryProgress(percent);
                    mHandler.postDelayed(this, 1000);
                }
            }
        }
    };


    private void FillModelList(String baseurl) {
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid1.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "video17", "1M", "Android Declarative layout using Data Binding Library)"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid2.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video1", "4.5M", "Android ListView - Simple ListView Filter_Search - YouTube [360p]"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid3.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "video2", "400K", "Android Menus Ep.01 _ Circular Floating Menu - Events and Animation - YouTube [360p]"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid4.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "video4", "544K", "IIS Installation and Configuration ASP.NET Part-1"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid5.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/1_pyn1fm.jpg", "video5", "10000", "How to Integrate Facebook Login with your Android Application"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid6.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1491561340/hello_cuwgcb.jpg", "video6", "12.7M", "Part 1 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid7.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video9", "90", "Part 2 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc]"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid8.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795676/4_nvnzry.jpg", "video11", "89K", "Part 5 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid9.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video12", "899", "Part 4 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid10.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795675/3_yqeudi.jpg", "video16", "6K", "Part 6 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc"));
        modelList.add(new MyModel(baseurl + "/Elearning/vids/vid11.mp4", "http://res.cloudinary.com/krupen/video/upload/w_300,h_150,c_crop,q_70,so_0/v1481795681/2_rp0zyy.jpg", "video18", "120K", "Part 14 - Complete Web Application step by step using ASP.NET MVC 5, EF, Ninject, LINQ etc"));
    }

    public void LoadData(final Map<String, String> params) {
        pg2.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, new SpaceCraft().getUrl(getContext()), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                requestQueue.stop();
                ParseData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                requestQueue.stop();
                pg2.setVisibility(View.GONE);
                ToggleMessage("Connection Error");
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public void ParseData(String json) {
        new AsyncTsk().execute();
    }

    public void LoadMoreData(Map<String, String> params) {

    }

    public void ParseLoadMoreData(String json) {

    }

    class AsyncTsk extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            FillModelList(new SpaceCraft().ipPort(getContext()));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (!modelList.isEmpty()) {
                pg2.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setAdapter(new RecyclerAdapter(VideoFragment.this, getContext(), modelList));
            }
        }
    }


    SwipeRefreshLayout swipeRefreshLayout;
    static RequestQueue requestQueue;
    static final Map<String, String> paramsMain = new HashMap<>();


}
