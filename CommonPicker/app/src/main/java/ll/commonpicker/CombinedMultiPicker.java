package ll.commonpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 3-4 0004.
 */

public class CombinedMultiPicker extends AbstractPicker implements View.OnClickListener {
    private Button mCancel;
    private Button mConfirm;
    private PickerRecyclerView mContaineRv;
    private PickerRecyclerView mContaineRv2;
    private List<ItemBean> mDataList;
    private List<List<ItemBean>> mDataList2;
    private List<ItemBean> mSecondDataList;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    private ContainAdapter mAdapter;
    private ContainAdapter mAdapter2;
    private static final String TAG = "PengLog";

    public CombinedMultiPicker(Context context) {
        super(context);
    }

    public CombinedMultiPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CombinedMultiPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picker_multi_layout;
    }

    @Override
    public void init() {
        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:

                break;

            case R.id.confirm:
                int index = mContaineRv.getMiddleItemPosition();
                int index2 = mContaineRv2.getMiddleItemPosition();
                Toast.makeText(mContext, "当前选中 " + mDataList.get(index).getName() + "   " + mDataList2.get(index - 2).get(index2).getName(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void setData(List<ItemBean> dataList, List<List<ItemBean>> dataList2) {
        mDataList = dataList;
        mDataList2 = dataList2;
        mDataList = makeList(mDataList);
        mDataList2 = makeCombinedList(mDataList2);
        mSecondDataList = new ArrayList<>();
        mSecondDataList = mDataList2.get(2);
        initData();

    }

    private void initViews() {
        mCancel = findViewById(R.id.cancel);
        mConfirm = findViewById(R.id.confirm);
        mContaineRv = findViewById(R.id.container_rv);
        mContaineRv2 = findViewById(R.id.container_rv2);
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }


    private void initData() {

        mAdapter = new ContainAdapter(mContext, mDataList);

        mContaineRv.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mContaineRv.setAdapter(mAdapter);
        mLayoutManager.scrollToPosition(2);

        mContaineRv2.setLayoutManager(mLayoutManager2 = new LinearLayoutManager(mContext));
        setSecondAdapter();

        mContaineRv.setScrollPositionChange(new PickerRecyclerView.ScrollPositionChange() {
            @Override
            public void onPositionChange(int position) {
                Log.d(TAG, "onPositionChange = " + position);
                mSecondDataList = mDataList2.get(position);
                //这里有个bug，直接修改mSecondDataList后调用mAdapter2.notifyDataSetChanged()不生效
                setSecondAdapter();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setSelectedSecondAdapter();
                    }
                }, 200);
            }
        });

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //这里是默认选中中间的item
                mContaineRv.setDataList(mDataList);
                mContaineRv.refreshData(mContaineRv.getMiddleItemPosition());
                mAdapter.notifyDataSetChanged();

                setSelectedSecondAdapter();

            }
        }, 200);
    }


    //设置第二个RecyclerView的adapter
    private void setSecondAdapter() {
        mAdapter2 = new ContainAdapter(mContext, mSecondDataList);
        mContaineRv2.setAdapter(mAdapter2);
        mLayoutManager2.scrollToPosition(2);
    }

    //设置第二个adapter默认选中和高亮的位置
    private void setSelectedSecondAdapter() {
        mContaineRv2.setDataList(mSecondDataList);
        mContaineRv2.refreshData(mContaineRv2.getMiddleItemPosition());
        mAdapter2.notifyDataSetChanged();
    }
}
