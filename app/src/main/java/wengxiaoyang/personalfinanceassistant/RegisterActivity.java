package wengxiaoyang.personalfinanceassistant;

import android.support.v4.app.Fragment;

public class RegisterActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new RegisterFragment();
    }
}

