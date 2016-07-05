package com.singtel.groupit.uiutil;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by lanna on 6/24/16.
 *
 */

public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

    private int itemOffset;
    private int cols;
    private boolean hasOutSidePadding;

    public ItemOffsetDecoration(int itemOffset, int cols, boolean hasOutSidePadding) {
        this.itemOffset = itemOffset;
        this.cols = cols;
        this.hasOutSidePadding = hasOutSidePadding;

    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId,
                                int cols, boolean hasOutSidePadding) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId), cols, hasOutSidePadding);
    }

    public ItemOffsetDecoration(int itemOffset, boolean hasOutSidePadding) {
        this(itemOffset, 1, hasOutSidePadding);
    }

    public ItemOffsetDecoration(@NonNull Context context, @DimenRes int itemOffsetId, boolean hasOutSidePadding) {
        this(context, itemOffsetId, 1, hasOutSidePadding);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view);
        int colIndex = position % cols;
        int rowIndex = position / cols;

        // reset data
//            outRect.left = outRect.top = 0;

        // item at left or right side
        if (colIndex < cols - 1 // others except right
                || (hasOutSidePadding && colIndex == cols - 1) // include right if required
                ) {
            outRect.right = itemOffset;
        }

        // item at top or bottom side
        if (rowIndex > 0 // others except top
                || (hasOutSidePadding && rowIndex == 0) // include top if required
                ) {
            if(getOrientation(parent) == LinearLayoutManager.HORIZONTAL){
                outRect.left = itemOffset;
            }else {
                outRect.top = itemOffset;
            }
        }



//            LogUtils.i(this, "getItemOffsets: pos:" + position// + ", outside:" + hasOutSidePadding
//                    + ", row:" + rowIndex + ", col:" + colIndex
//                    + " ->ltrb:(" + outRect.left + "," + outRect.top + "-" + outRect.right + "," + outRect.bottom + ")");
    }


    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            return -1;
        }
    }
}
