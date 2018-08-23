package com.aviator.elearning.aviator.main;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Aviator on 3/27/2018.
 */

public class MyDecor extends RecyclerView.ItemDecoration {
    private int space;

    public MyDecor(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            int ori = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
            if (ori == LinearLayoutManager.VERTICAL) {
                outRect.set(0, space, 0, space);
            } else {
                outRect.set(space, space, space, space);
            }
        }
    }
}
