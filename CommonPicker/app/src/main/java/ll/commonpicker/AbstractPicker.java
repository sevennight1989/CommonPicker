package ll.commonpicker;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 3-4 0004.
 */

public abstract class AbstractPicker extends RelativeLayout {
    protected Handler mHandler;
    protected Context mContext;

    public AbstractPicker(Context context) {
        this(context, null);
    }

    public AbstractPicker(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AbstractPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHandler = new Handler();
        initBaseView();
        init();
    }

    private void initBaseView() {
        View.inflate(mContext, getLayoutId(), this);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    public abstract void init();

    protected List<List<ItemBean>> makeCombinedList(List<List<ItemBean>> list) {
        List<List<ItemBean>> newCombinedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            newCombinedList.add(makeList(list.get(i)));
        }
        return newCombinedList;
    }

    protected List<ItemBean> makeList(List<ItemBean> list) {
        List<ItemBean> newList = new ArrayList<>();
        ItemBean itemBean = new ItemBean();
        itemBean.setName("");
        itemBean.setHighLight(false);
        newList.add(itemBean);
        newList.add(itemBean);
        newList.addAll(list);
        newList.add(itemBean);
        newList.add(itemBean);
        return newList;
    }


}
