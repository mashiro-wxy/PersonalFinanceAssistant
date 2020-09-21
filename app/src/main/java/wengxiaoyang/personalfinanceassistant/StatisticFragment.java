package wengxiaoyang.personalfinanceassistant;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticFragment extends Fragment {

    private EditText mYear;
    private EditText mMonth;
    private EditText mDay;
    private Button mButton;
    private TextView mMoney;
    private Double totalMoney = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 布局
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        // 年
        mYear = view.findViewById(R.id.edit_year);

        // 月
        mMonth = view.findViewById(R.id.edit_month);

        // 日
        mDay = view.findViewById(R.id.edit_day);

        mButton = view.findViewById(R.id.button_statistic);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取输入框内容
                String year = mYear.getText().toString();
                String month = mMonth.getText().toString();
                String day = mDay.getText().toString();
                // 查找到所有记录
                List<Manage> manages = ManageLab.get(getActivity()).getManages();
                // 匹配有相同时间的记录,获得其金额并相加
                for (int i = 0; i < manages.size(); i++){
                    if (year.equals(manages.get(i).getDate().toString().substring(24,28)) || month.equals(manages.get(i).getDate().toString().substring(4,7)) || day.equals(manages.get(i).getDate().toString().substring(8,10))) {
                        totalMoney += Double.parseDouble(manages.get(i).getMoney());
                    }
                }
                mMoney.setText(totalMoney.toString() + "元");
                totalMoney = 0.0;
            }
        });

        mMoney = view.findViewById(R.id.text_money);

        return view;
    }
}
