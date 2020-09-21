package wengxiaoyang.personalfinanceassistant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class ManagePagerActivity extends AppCompatActivity {

    private static final String EXTRA_MANAGE_ID = "wengxiaoyang.personalfinanceassistant.manage_id";
    private static final String EXTRA_MANAGE_TITLE = "wengxiaoyang.personalfinanceassistant.manage_title";

    private ViewPager mViewPager;
    private List<Manage> mManages;
    private Button btn_first;
    private Button btn_last;

    public static Intent newIntent(Context packageContext, UUID manageId) {
        Intent intent = new Intent(packageContext, ManagePagerActivity.class);
        intent.putExtra(EXTRA_MANAGE_ID, manageId);
        return intent;
    }

    public static Intent newIntent(Context packageContext, String title) {
        Intent intent = new Intent(packageContext, ManagePagerActivity.class);
        intent.putExtra(EXTRA_MANAGE_TITLE, title);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_pager);

        UUID manageId = (UUID) getIntent().getSerializableExtra(EXTRA_MANAGE_ID);

        mViewPager = findViewById(R.id.manage_view_pager);
        // 根据所处位置隐藏按钮
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    btn_first.setVisibility(View.INVISIBLE);
                    btn_last.setVisibility(View.VISIBLE);
                } else if (position == mManages.size() - 1) {
                    btn_first.setVisibility(View.VISIBLE);
                    btn_last.setVisibility(View.INVISIBLE);
                } else {
                    btn_first.setVisibility(View.VISIBLE);
                    btn_last.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        btn_first = findViewById(R.id.btn_to_first);
        btn_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });

        btn_last = findViewById(R.id.btn_to_last);
        btn_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mManages.size() - 1);
            }
        });

        mManages = ManageLab.get(this).getManages();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Manage manage = mManages.get(position);
                return ManageFragment.newInstance(manage.getId());
            }

            @Override
            public int getCount() {
                return mManages.size();
            }
        });

        // 给每条记录编号
        for (int i = 0; i < mManages.size(); i++) {
            if (mManages.get(i).getId().equals(manageId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
