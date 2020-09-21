package wengxiaoyang.personalfinanceassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SearchActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new SearchFragment();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SearchActivity.class);
        return intent;
    }

}
