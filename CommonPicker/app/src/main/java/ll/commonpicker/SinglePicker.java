package ll.commonpicker;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 3-3 0003.
 */

public class SinglePicker extends AbstractPicker implements View.OnClickListener {
    private Button mCancel;
    private Button mConfirm;
    private PickerRecyclerView mContaineRv;
    private List<ItemBean> mDataList;
    private LinearLayoutManager mLayoutManager;
    private ContainAdapter mAdapter;

    private static final String TAG = "PengLog";

    public SinglePicker(Context context) {
        super(context);
    }

    public SinglePicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SinglePicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.picker_single_layout;
    }


    @Override
    public void init() {
        initViews();
    }

    public void setData(List<ItemBean> dataList) {
        mDataList = dataList;
        mDataList = makeList(mDataList);
        initData();
    }

    private void initViews() {
        mCancel = findViewById(R.id.cancel);
        mConfirm = findViewById(R.id.confirm);
        mContaineRv = findViewById(R.id.container_rv);
        mCancel.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
    }


    private void initData() {
        mAdapter = new ContainAdapter(mContext, mDataList);

        mContaineRv.setLayoutManager(mLayoutManager = new LinearLayoutManager(mContext));
        mContaineRv.setAdapter(mAdapter);
        mLayoutManager.scrollToPosition(2);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mContaineRv.setDataList(mDataList);
                mContaineRv.refreshData(mContaineRv.getMiddleItemPosition());
                mAdapter.notifyDataSetChanged();
            }
        }, 200);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:

                break;

            case R.id.confirm:
                int index = mContaineRv.getMiddleItemPosition();
                Log.d(TAG, "当前选中的 " + mDataList.get(index).getName());
                Toast.makeText(mContext, "当前选中 " + mDataList.get(index).getName(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
