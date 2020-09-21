package wengxiaoyang.personalfinanceassistant;

import android.support.v4.app.Fragment;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginFragment extends Fragment {

    // UI
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mRegisterButton;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // 建立视图
        mEmailView = (AutoCompleteTextView) view.findViewById(R.id.email);

        mPasswordView = (EditText) view.findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) view.findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();

            }
        });


        mRegisterButton = view.findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        mLoginFormView = view.findViewById(R.id.login_form);
        mProgressView = view.findViewById(R.id.login_progress);

        return view;
    }

    private void attemptLogin() {

        // 重置
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // 获取登录的信息
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 校验账号和密码
        if (login(email, password)) {
            mEmailView.setError(getString(R.string.error_error_acpw));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // 聚焦到错误发生地
            focusView.requestFocus();
        } else {
            // 成功登录
            showProgress(true);
            // 跳转到ManageListActivity
            Intent intent = ManageListActivity.newIntent(getActivity());
            startActivity(intent);

        }
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    // 动画
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // 若API不支持,则简单显示
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public boolean login(String account, String password) {//验证此账号密码是否正确
        Login login = LoginLab.get(getActivity()).getAccountAndPassword(account, password);
        if (login != null) {
            return false;
        } else
            return true;
    }
}
