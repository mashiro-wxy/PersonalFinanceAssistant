package wengxiaoyang.personalfinanceassistant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class ManageListFragment extends Fragment {

    private RecyclerView mManageRecyclerView;
    private ManageAdapter mAdapter;
    private static int mManageIndex;
    private boolean mSubtitleVisible;
    private TextView mNullManageListTextView;
    private Button mAddManageButton;

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    // UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_list, container, false);

        mManageRecyclerView =  view.findViewById(R.id.manage_recycler_view);
        mManageRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mNullManageListTextView = view.findViewById(R.id.null_manage_list);

        mAddManageButton = view.findViewById(R.id.add_manage);
        mAddManageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Manage manage = new Manage();
                ManageLab.get(getActivity()).addManage(manage);
                Intent intent = ManagePagerActivity.newIntent(getActivity(), manage.getId());
                startActivity(intent);
            }
        });

        // 保持记录条数可见
        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    // 创建菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_manage_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    // 响应菜单点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_manage:   // 创建新记录
                Manage manage = new Manage();
                ManageLab.get(getActivity()).addManage(manage);
                Intent intent = ManagePagerActivity.newIntent(getActivity(), manage.getId());
                startActivity(intent);
                return true;
            case R.id.search_manage:    //搜索
                intent = SearchActivity.newIntent(getActivity());
                startActivity(intent);
                return true;
            case R.id.statistic_manage: //统计
                intent = new Intent(getActivity(), StatisticActivity.class);
                startActivity(intent);
                return true;
            case R.id.show_subtitle:    //显示记录条数
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 更新记录条数
    private void updateSubtitle() {
        ManageLab manageLab = ManageLab.get(getActivity());
        int manageCount = manageLab.getManages().size();
        String subtitle = getString(R.string.subtitle_format, manageCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    // 更新UI界面
    private void updateUI() {
        ManageLab manageLab = ManageLab.get(getActivity());
        List<Manage> manages = manageLab.getManages();

        if (mAdapter == null) {
            mAdapter = new ManageAdapter(manages);
            mManageRecyclerView.setAdapter(mAdapter);
        } else {
            //重绘当前可见区域
            //mAdapter.notifyDataSetChanged();

            //部分重绘
            mAdapter.setManages(manages);
            mAdapter.notifyItemChanged(mManageIndex);
        }

        if (manages.size() != 0) {
            mNullManageListTextView.setVisibility(View.INVISIBLE);
            mAddManageButton.setVisibility(View.INVISIBLE);
        } else {
            mNullManageListTextView.setVisibility(View.VISIBLE);
            mAddManageButton.setVisibility(View.VISIBLE);
        }

        updateSubtitle();
    }

    private class ManageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Manage mManage;

        public ManageHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_manage, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.manage_title);
            mDateTextView = itemView.findViewById(R.id.manage_date);
        }

        public void bind(Manage manage) {
            mManage = manage;
            mTitleTextView.setText(mManage.getTitle());

            Date date = manage.getDate();
            /*DateFormat format = new SimpleDateFormat("EEE, MMM dd, YYYY");//星期,月份 ,几号,年份
            String dateFormat = format.format(date);*/
            mDateTextView.setText(date.toString());
        }

        @Override
        public void onClick(View view) {
            Intent intent = ManagePagerActivity.newIntent(getActivity(), mManage.getId());
            mManageIndex = getAdapterPosition();     //返回数据在adapter中的位置
            startActivity(intent);
        }
    }


    private class ManageAdapter extends RecyclerView.Adapter {

        private List<Manage> mManages;

        public ManageAdapter(List<Manage> manages) {
            mManages = manages;
        }
        //视图类别功能

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //先建立LayoutInflater
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ManageHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Manage manage = mManages.get(position);
            ((ManageHolder)holder).bind(manage);

        }

        @Override
        public int getItemCount() {
            return mManages.size();
        }

        public void setManages(List<Manage> manages) {
            mManages = manages;
        }
    }


}
