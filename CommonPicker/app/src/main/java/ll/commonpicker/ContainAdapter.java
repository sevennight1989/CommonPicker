package ll.commonpicker;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Percy on 3-3 0003.
 */

public class ContainAdapter extends RecyclerView.Adapter<ContainAdapter.ContainViewHolder> {

    private Context mContext;
    private List<ItemBean> mCurrentDataList;
    private int length;

    public ContainAdapter(Context context, List<ItemBean> dataList) {
        mCurrentDataList = new ArrayList<>();
        this.mContext = context;
        this.mCurrentDataList = dataList;
        if (mCurrentDataList != null) {
            length = mCurrentDataList.size();
        }
    }

    @NonNull
    @Override
    public ContainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.container_item, null);
        return new ContainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContainViewHolder holder, int position) {
        holder.item_text.setText(mCurrentDataList.get(position).getName());
        //开始两个和最后两个不显示
        if (position == 0 || position == 1 || position == (length - 2) || position == (length - 1)) {
            holder.item_text.setVisibility(View.INVISIBLE);
        } else {
            holder.item_text.setVisibility(View.VISIBLE);
        }
        TextPaint tp = holder.item_text.getPaint();
        if (mCurrentDataList.get(position).isHighLight()) {
            holder.item_text.setTextColor(Color.RED);
            holder.item_text.setTextSize(23);
            tp.setFakeBoldText(true);
        } else {
            holder.item_text.setTextColor(Color.BLACK);
            holder.item_text.setTextSize(20);
            tp.setFakeBoldText(false);
        }
    }

    @Override
    public int getItemCount() {
        return mCurrentDataList.size();
    }


    class ContainViewHolder extends RecyclerView.ViewHolder {
        TextView item_text;

        public ContainViewHolder(View itemView) {
            super(itemView);
            item_text = itemView.findViewById(R.id.item_text);
        }
    }


}