package ll.commonpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * Created by Percy on 3-3 0003.
 */

public class PickerRecyclerView extends RecyclerView {

    public interface ScrollPositionChange {
        void onPositionChange(int position);
    }

    private ScrollPositionChange mScrollPositionChange;

    public void setScrollPositionChange(ScrollPositionChange mScrollPositionChange) {
        this.mScrollPositionChange = mScrollPositionChange;
    }

    private ContainAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    int offsetMax = 0;
    private List<ItemBean> mDataList;
    private static final String TAG = "PengLog";


    public PickerRecyclerView(Context context) {
        this(context, null);
    }

    public PickerRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PickerRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        offsetMax = dip2px(context, 40f);
    }

    public void setAdapter(ContainAdapter adapter) {
        this.mAdapter = adapter;
        super.setAdapter(mAdapter);
    }


    public void setLayoutManager(LinearLayoutManager mLayoutManager) {
        this.mLayoutManager = mLayoutManager;
        super.setLayoutManager(mLayoutManager);
    }


    public void setDataList(List<ItemBean> dataList) {
        mDataList = dataList;
        addOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                Log.d(TAG, "newState = " + newState);
                //RecyclerView滑动停止
                if (newState == SCROLL_STATE_IDLE) {
                    int middlePosition = getMiddleItemPosition();
                    mLayoutManager.scrollToPositionWithOffset(middlePosition - 2, 0);
                    if (mScrollPositionChange != null) {
                        mScrollPositionChange.onPositionChange(middlePosition-2);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                refreshData(getMiddleItemPosition());
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void refreshData(int index) {
        for (int i = 0; i < mDataList.size(); i++) {
            if (i == index) {
                mDataList.get(i).setHighLight(true);
            } else {
                mDataList.get(i).setHighLight(false);
            }
        }
    }


    public int getMiddleItemPosition() {
        int[] pos = getRecyclerViewLastPosition(mLayoutManager);
        int index = pos[0];
        int offest = pos[1];
        //计算偏移量，小于item高度一半的需要index+1
        if (offest < offsetMax / 2) {
            index += 1;
        }
        return index;
    }


    //获得RecyclerView最后的位置
    private int[] getRecyclerViewLastPosition(LinearLayoutManager layoutManager) {
        int[] pos = new int[2];
        int first = layoutManager.findFirstCompletelyVisibleItemPosition();
        //+1 表示第一个完全可见的下一个
        pos[0] = first + 1;
        OrientationHelper orientationHelper = OrientationHelper.createOrientationHelper(layoutManager, OrientationHelper.VERTICAL);
        int fromIndex = 0;
        int toIndex = mDataList.size();
        final int start = orientationHelper.getStartAfterPadding();
        final int end = orientationHelper.getEndAfterPadding();
        final int next = toIndex > fromIndex ? 1 : -1;
        for (int i = fromIndex; i != toIndex; i += next) {
            final View child = getChildAt(i);
            final int childStart = orientationHelper.getDecoratedStart(child);
            final int childEnd = orientationHelper.getDecoratedEnd(child);
            if (childStart < end && childEnd > start) {
                if (childStart >= start && childEnd <= end) {
                    pos[1] = childStart;
//                    Log.d(TAG, "position = " + pos[0] + " off = " + pos[1]);
                    return pos;
                }
            }
        }
        return pos;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
