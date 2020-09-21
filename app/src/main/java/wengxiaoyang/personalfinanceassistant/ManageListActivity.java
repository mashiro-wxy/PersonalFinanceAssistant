package wengxiaoyang.personalfinanceassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ManageListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new ManageListFragment();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, ManageListActivity.class);
        return intent;
    }
}
