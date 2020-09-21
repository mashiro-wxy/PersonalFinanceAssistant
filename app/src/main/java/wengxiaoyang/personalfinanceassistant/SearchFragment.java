package wengxiaoyang.personalfinanceassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchFragment extends Fragment {

    // UI
    private TextView mTextView;
    private EditText mSearchView;
    private Button mSearchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 布局
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mTextView = view.findViewById(R.id.textView_search);

        mSearchView = (EditText) view.findViewById(R.id.edit_search);


        mSearchButton  = (Button) view.findViewById(R.id.button_search1);
        // 跳转到搜索的activity
        mSearchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String search = mSearchView.getText().toString();
                Intent intent = ManagePagerActivity.newIntent(getActivity(), search);
                startActivity(intent);
            }
        });

        return view;
    }
}
