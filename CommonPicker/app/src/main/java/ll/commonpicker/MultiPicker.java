package ll.commonpicker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Percy on 3-4 0004.
 */

public class MultiPicker extends AbstractPicker implements View.OnClickListener {
    private Button mCancel;
    private Button mConfirm;
    private PickerRecyclerView mContaineRv;
    private PickerRecyclerView mContaineRv2;
    private List<ItemBean> mDataList;
    private List<ItemBean> mDataList2;
    private LinearLayoutManager mLayoutManager;
    private LinearLayoutManager mLayoutManager2;
    private ContainAdapter mAdapter;
    private ContainAdapter mAdapter2;
    private static final String TAG = "PengLog";
    public MultiPicker(Context context) {
        super(context);
    }

    public MultiPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
                Toast.makeText(mContext, "当前选中 " + mDataList.get(index).getName()+"   " + mDataList2.get(index2).getName(), Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void setData(List<ItemBean> dataList, List<ItemBean> dataList2) {
        mDataList = dataList;
        mDataList2 = dataList2;
        mDataList = makeList(mDataList);
        mDataList2 = makeList(mDataList2);
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

        mAdapter2 = new ContainAdapter(mContext, mDataList2);

        mContaineRv2.setLayoutManager(mLayoutManager2 = new LinearLayoutManager(mContext));
        mContaineRv2.setAdapter(mAdapter2);
        mLayoutManager2.scrollToPosition(2);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mContaineRv.setDataList(mDataList);
                mContaineRv.refreshData(mContaineRv.getMiddleItemPosition());
                mAdapter.notifyDataSetChanged();

                mContaineRv2.setDataList(mDataList2);
                mContaineRv2.refreshData(mContaineRv2.getMiddleItemPosition());
                mAdapter2.notifyDataSetChanged();
            }
        }, 200);
    }
}
