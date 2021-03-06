package com.example.jobbook.ui.account.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.account.RegisterContract;
import com.example.jobbook.model.bean.PersonBean;
import com.example.jobbook.presenter.account.RegisterPresenter;
import com.example.jobbook.ui.main.activity.MainActivity;
import com.example.jobbook.util.SMSSDKManager;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/6/2.
 */
public class RegisterActivity extends Activity implements RegisterContract.View, SMSSDKManager.TimeListener {

    @BindView(R.id.register_register_bt)
    Button mRegisterButton;

    @BindView(R.id.register_account_et)
    EditText mAccountEditText;

    @BindView(R.id.register_username_et)
    EditText mUserNameEditText;

    @BindView(R.id.register_password_et)
    EditText mPwdEditText;

    @BindView(R.id.register_confirm_password_et)
    EditText mPwdAgainEditText;

    @BindView(R.id.register_code_et)
    EditText mCodeEditText;

    @BindView(R.id.register_code_bt)
    Button mGetCodeButton;

    @BindView(R.id.register_close_ib)
    ImageButton mCloseImageButton;

    @BindView(R.id.activity_register_loading)
    ViewStub mLoadingLinearLayout;

    @BindView(R.id.activity_register_layout)
    LinearLayout mParentLayout;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        presenter = new RegisterPresenter(this);
        SMSSDKManager.getInstance().registerTimeListener(this);
    }

    @Override
    public void success() {
        SnackBarUtil.showSnackBar(this, "连接成功");
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void accountBlankError() {
        SnackBarUtil.showSnackBar(this, "账号为空");
    }

    @Override
    public void pwdBlankError() {
        SnackBarUtil.showSnackBar(this, "密码为空");
    }

    @Override
    public void pwdConfirmBlankError() {
        SnackBarUtil.showSnackBar(this, "确认密码为空");
    }

    @Override
    public void pwdNotEqualError() {
        SnackBarUtil.showSnackBar(this, "密码与确认密码不一致");
    }

    @Override
    public void switch2Person(PersonBean personBean) {
        MyApplication.setmPersonBean(RegisterActivity.this, personBean);
        Util.toAnotherActivity(RegisterActivity.this, MainActivity.class);
        finish();
    }

    @Override
    public void accountIllegalError() {
        SnackBarUtil.showSnackBar(this, "账号存在非法字符");
    }

    @Override
    public void userNameBlankError() {
        SnackBarUtil.showSnackBar(this, "昵称为空");
    }

    @Override
    public void codeBlankError() {
        SnackBarUtil.showSnackBar(this, "验证码为空");
    }

    @Override
    public void codeError() {
        SnackBarUtil.showSnackBar(this, "验证码错误");
//        refreshCode();
    }

    @OnClick(R.id.register_register_bt)
    public void register() {
        presenter.registerCheck(RegisterActivity.this, mAccountEditText.getText().toString(),
                mUserNameEditText.getText().toString(), mPwdEditText.getText().toString(), mPwdAgainEditText.getText().toString(),
                mCodeEditText.getText().toString());
    }

    @OnClick(R.id.register_close_ib)
    public void register_close() {
        Util.toAnotherActivity(RegisterActivity.this, LoginActivity.class);
        finish();
    }

    @OnClick(R.id.register_code_bt)
    public void get_code() {
        if (!TextUtils.isEmpty(mAccountEditText.getText().toString())) {
            SMSSDKManager.getInstance().sendMessage(RegisterActivity.this, "86", mAccountEditText.getText().toString());
        } else {
            codeBlankError();
        }
    }

    @OnClick(R.id.activity_register_layout)
    public void click_layout(View v) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDKManager.getInstance().unRegisterTimeListener(this);
    }

    @Override
    public void onLastTimeNotify(int lastSecond) {
        if (lastSecond > 0) {
            mGetCodeButton.setText(lastSecond + "s");
        } else {
            mGetCodeButton.setText("发送验证码");
        }
    }

    @Override
    public void onAbleNotify(boolean valuable) {
        mGetCodeButton.setEnabled(valuable);
    }

}
