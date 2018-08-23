package com.brucetoo.videoplayer.scrolldetector;

import android.view.View;

import com.brucetoo.videoplayer.tracker.IViewTracker;

/**
 * Created by Bruce Too
 * On 05/04/2017.
 * At 22:33
 */

public interface IScrollDetector {

    int SCROLL_STATE_IDLE = 0;

    int SCROLL_STATE_TOUCH_SCROLL = 1;

    int SCROLL_STATE_FLING = 2;

    View getView();

    void setTracker(IViewTracker tracker);

    void onScrollStateChanged(int scrollState);

    void detach();

    boolean isIdle();
}
