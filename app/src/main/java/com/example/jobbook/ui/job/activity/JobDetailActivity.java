package com.example.jobbook.ui.job.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.job.JobDetailContract;
import com.example.jobbook.model.bean.JobBean;
import com.example.jobbook.model.bean.JobDetailBean;
import com.example.jobbook.presenter.job.JobDetailPresenter;
import com.example.jobbook.ui.account.activity.LoginActivity;
import com.example.jobbook.util.L;
import com.example.jobbook.util.ScreenUtil;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/7/13.
 */
public class JobDetailActivity extends Activity implements JobDetailContract.View {

    @BindView(R.id.job_detail_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.job_detail_tocompany_tv)
    TextView mToCompanyDetailTextView;

    @BindView(R.id.job_detail_like_ib)
    ImageButton mLikeImageButton;

    @BindView(R.id.activity_job_detail_loading)
    ViewStub mLoadingLinearLayout;

    @BindView(R.id.job_detail_name_tv)
    TextView mJobNameTextView;

    @BindView(R.id.job_detail_location_tv)
    TextView mJobLocationTextView;

    @BindView(R.id.job_detail_time_tv)
    TextView mJobTimeTextView;

    @BindView(R.id.job_detail_salary_tv)
    TextView mSalaryTextView;

    @BindView(R.id.job_detail_company_name_tv)
    TextView mCompanyNameTextView;

    @BindView(R.id.job_detail_company_location_tv)
    TextView mCompanyLocationTextView;

    @BindView(R.id.job_detail_company_description_tv)
    TextView mCompanyDescriptionTextView;

    @BindView(R.id.job_detail_description_duty_content_tv)
    TextView mJobDutyTextView;

    @BindView(R.id.job_detail_description_require_content_tv)
    TextView mJobRequireTextView;

    @BindView(R.id.job_detail_benefit_tv)
    TextView mBenefitTextView;

    @BindView(R.id.job_detail_send_cv_ll)
    LinearLayout mSendCVLayout;

    private JobDetailPresenter mPresenter;
    private JobBean jobBean;
    private JobDetailBean jobDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        jobDetailBean = new JobDetailBean();
        initEvents();
    }

    private void initEvents() {
        jobBean = getIntent().getExtras().getParcelable("job_detail");
        mPresenter = new JobDetailPresenter(this);
        mPresenter.loadJob(jobBean.getId());
    }

    @OnClick(R.id.job_detail_back_ib)
    public void click_back() {
        finish();
    }

    @OnClick(R.id.job_detail_tocompany_tv)
    public void click_job_detail_tocompany_tv() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(jobDetailBean.getCompany().getWebsite()));
            startActivity(intent);
        } catch (Exception e) {
            SnackBarUtil.showSnackBar(this, "打开公司网址失败！");
        }
    }

    @OnClick(R.id.job_detail_like_ib)
    public void click_job_detail_like_ib() {
        if (jobDetailBean.isIfLike() == 0) {
            L.i("click like");
            mPresenter.like(jobBean.getId());
        } else {
            L.i("click unlike");
            mPresenter.unlike(jobBean.getId());
        }
        refresh();
    }

    @OnClick(R.id.job_detail_send_cv_ll)
    public void send_cv() {
        if (MyApplication.getmLoginStatus() == 0) {
            SnackBarUtil.showSnackBar(this, "请先登录", "现在登录", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.toAnotherActivity(JobDetailActivity.this, LoginActivity.class);
                    finish();
                }
            });
        } else {
            sendCVCheckDialog();
        }
    }

    @Override
    public void addJob(JobDetailBean jobDetailBean) {
        this.jobDetailBean = jobDetailBean;
        L.i(jobDetailBean.getCompany().toString());
//        ImageLoadUtils.display(this, mCompanyImageView, jobDetailBean.getCompany().getLogo());
        mJobNameTextView.setText(jobDetailBean.getName());
        mJobLocationTextView.setText(jobDetailBean.getLocation());
        mJobTimeTextView.setText(jobDetailBean.getTime());
        mSalaryTextView.setText(jobDetailBean.getSalary());
        mCompanyNameTextView.setText(jobDetailBean.getCompany().getName());
        mCompanyLocationTextView.setText(jobDetailBean.getCompany().getLocation());
        mCompanyDescriptionTextView.setText(jobDetailBean.getCompany().getScale());
        mJobDutyTextView.setText(jobDetailBean.getResponsibilities());
        mJobRequireTextView.setText(jobDetailBean.getRequirements());
        String welfare = jobDetailBean.getCompany().getWelfare().replace(",", " • ");
        mBenefitTextView.setText(welfare);
        if (MyApplication.getmLoginStatus() == 1) {
            L.i(jobDetailBean.isIfLike() + "");
            if (jobDetailBean.isIfLike() == 0) {
                mLikeImageButton.setImageResource(R.mipmap.favourite_white);
            } else {
                mLikeImageButton.setImageResource(R.mipmap.favourite);
            }
        }
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
    public void NoLoginError() {
        SnackBarUtil.showSnackBar(this, "请先登录", "现在登录", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toAnotherActivity(JobDetailActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    @Override
    public void likeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite);
        SnackBarUtil.showSnackBar(this, "收藏成功！");
    }

    @Override
    public void unlikeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_white);
        SnackBarUtil.showSnackBar(this, "取消收藏成功！");
    }

//    @Override
//    public void likeError() {
//        SnackBarUtil.showSnackBar(this, "收藏失败，请重试！");
//    }
//
//    @Override
//    public void unlikeError() {
//        SnackBarUtil.showSnackBar(this, "取消收藏失败，请重试！");
//    }

    @Override
    public void sendCVSuccess() {
        SnackBarUtil.showSnackBar(this, "您的简历发送成功！");
    }

    private void refresh() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        onCreate(null);
    }

    private void sendCVCheckDialog() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
//        p.width = Util.dip2px(this, 280);
//        p.height = Util.dip2px(this, 109);
        p.width = ScreenUtil.dip2px(this, 300);
        p.height = ScreenUtil.dip2px(this, 140);
        window.setAttributes(p);
        window.setContentView(R.layout.send_cv_check_layout);
        TextView mSureTextView = (TextView) window.findViewById(R.id.send_cv_sure_tv);
        TextView mCancelTextView = (TextView) window.findViewById(R.id.send_cv_cancel_tv);
        mSureTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.sendCV(jobDetailBean.getCompany().getId());
                alertDialog.dismiss();
            }
        });
        mCancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
