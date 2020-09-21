package wengxiaoyang.personalfinanceassistant;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class LoginActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        return intent;
    }

}

