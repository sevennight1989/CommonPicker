package ll.commonpicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ItemBean> mDataList;
    private List<List<ItemBean>> mDataList2;
    private CombinedMultiPicker cbmultiPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataList = new ArrayList<>();
        mDataList2 = new ArrayList<>();
        cbmultiPicker = findViewById(R.id.cb_multi_picker);

        for (int i = 0; i < 20; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.setName("item  " + i);
            itemBean.setHighLight(false);
            mDataList.add(itemBean);
        }

        for (int i = 0; i < 20; i++) {
            List<ItemBean> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                ItemBean itemBean = new ItemBean();
                itemBean.setName("item " + i + " - " + j);
                itemBean.setHighLight(false);
                list.add(itemBean);
            }
            mDataList2.add(list);

        }
        cbmultiPicker.setData(mDataList, mDataList2);


    }


}
