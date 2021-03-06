package com.example.jobbook.ui.person.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.MomentListContract;
import com.example.jobbook.model.bean.MomentBean;
import com.example.jobbook.ui.moment.activity.MomentDetailActivity;
import com.example.jobbook.presenter.person.MomentListPresenter;
import com.example.jobbook.ui.person.adapter.UserDetailMomentAdapter;
import com.example.jobbook.util.L;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Xu on 2017/1/16.
 */

public class ShowMomentListActivity extends Activity implements MomentListContract.View,
        UserDetailMomentAdapter.OnMomentItemClickListener {

    @BindView(R.id.momentlist_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.momentlist_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.momentlist_loading_layout)
    ViewStub mLoadingLinearLayout;

    private UserDetailMomentAdapter mAdapter;
    private MomentListPresenter presenter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_momentlist);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
        mAdapter = new UserDetailMomentAdapter(this, new ArrayList<MomentBean>());
        presenter = new MomentListPresenter(this);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnMomentItemClickListener(this);
        mAdapter.setOnMomentItemClickListener(this);
        if (MyApplication.getmLoginStatus() != 0) {
            presenter.loadMomentList(MyApplication.getAccount(), MyApplication.getAccount());
        }
    }

    @Override
    public void showProgress() {
        mLoadingLinearLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLinearLayout.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    @OnClick(R.id.momentlist_back_ib)
    public void back() {
        finish();
    }

    @Override
    public void onMomentItemClick(MomentBean momentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("square_detail", momentBean);
        L.i(momentBean.toString());
        Util.toAnotherActivity(this, MomentDetailActivity.class, bundle);
    }

    @Override
    public void loadMomentList(List<MomentBean> list) {
        mAdapter.refreshData(list);
    }
}
