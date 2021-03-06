package com.example.jobbook.ui.person.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.person.UserDetailFanContract;
import com.example.jobbook.model.bean.TypePersonBean;
import com.example.jobbook.presenter.person.UserDetailFanPresenter;
import com.example.jobbook.ui.person.activity.UserDetailActivity;
import com.example.jobbook.ui.person.adapter.UserDetailFansAdapter;
import com.example.jobbook.util.SnackBarUtil;
import com.example.jobbook.util.Util;
import com.example.jobbook.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Xu on 16-11-30.
 */

public class UserDetailFansFragment extends Fragment implements UserDetailFanContract.View,
        UserDetailActivity.OnGetAccountListener, UserDetailFansAdapter.OnFanItemClickListener,
        UserDetailFansAdapter.OnFollowFanClickListener {

    @BindView(R.id.base_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_base_lv_loading)
    ViewStub mLoadingLayout;

    private View view;
    private UserDetailFansAdapter mAdapter;
    private List<TypePersonBean> mData;
    private UserDetailFanPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private String account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_base_lv, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        mLoadingLayout.inflate();
        mData = new ArrayList<>();
        mPresenter = new UserDetailFanPresenter(this);
        mAdapter = new UserDetailFansAdapter(getActivity(), mData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnFollowFanClickListener(this);
        mAdapter.setOnFanItemClickListener(this);
        mPresenter.loadFans(account);
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void loadFans(List<TypePersonBean> mFans) {
        mAdapter.refreshData(mFans);
    }

    @Override
    public void showProgress() {
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void followSuccess() {
        SnackBarUtil.showSnackBar(getActivity(), "关注成功!");
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(getActivity(), msg);
    }

    @Override
    public void getAccount(String account) {
        this.account = account;
//        mPresenter.loadFans(account);
    }

    @Override
    public void onFanItemClick(TypePersonBean personBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("person_bean", personBean);
        Util.toAnotherActivity(getActivity(), UserDetailActivity.class, bundle);
    }

    @Override
    public void onFollowFanClick(TypePersonBean personBean) {
        mPresenter.follow(MyApplication.getAccount(), personBean.getAccount());
    }
}
