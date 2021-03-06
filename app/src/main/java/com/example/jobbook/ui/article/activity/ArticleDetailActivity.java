package com.example.jobbook.ui.article.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jobbook.R;
import com.example.jobbook.app.MyApplication;
import com.example.jobbook.base.contract.article.ArticleDetailContract;
import com.example.jobbook.model.bean.ArticleBean;
import com.example.jobbook.presenter.article.ArticleDetailPresenter;
import com.example.jobbook.util.SnackBarUtil;

import org.apache.commons.lang3.StringEscapeUtils;
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 椰树 on 2016/7/15.
 */
public class ArticleDetailActivity extends Activity implements ArticleDetailContract.View {

    private ArticleDetailPresenter mPresenter;
    private ArticleBean bean;
    private ArticleBean mArticleBean;
    private String cur_article_id;

    @BindView(R.id.article_detail_like_ib)
    ImageButton mLikeImageButton;

    @BindView(R.id.article_detail_back_ib)
    ImageButton mBackImageButton;

    @BindView(R.id.article_detail_content_reading_tv)
    TextView mReadingQuantityTextView;

    @BindView(R.id.article_detail_title_tv)
    TextView mArticleTitleTextView;

    @BindView(R.id.article_detail_content_time_tv)
    TextView mTimeTextView;

    @BindView(R.id.article_detail_content_tv)
    HtmlTextView mArticleContentTextView;

    @BindView(R.id.article_detail_loading)
    ViewStub mLoadingViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        initEvents();
    }

    private void initEvents() {
//        Util.setListViewHeightBasedOnChildren(mListView);
        mPresenter = new ArticleDetailPresenter(this);
        mArticleBean = getIntent().getExtras().getParcelable("article_detail");
        cur_article_id = mArticleBean.getArticle_id();
        mPresenter.loadArticle(mArticleBean.getArticle_id());
    }

    @Override
    public void showProgress() {
        mLoadingViewStub.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void addComments(List<ArticleCommentBean> mComments) {
////        mListView.setAdapter(new ArticleDetailListViewAdapter(this));
//    }

    @Override
    public void addArticle(ArticleBean mArticle) {
        bean = mArticle;
        mReadingQuantityTextView.setText(mArticle.getReadingquantity() + "");
        mArticleTitleTextView.setText(mArticle.getTitle());
        mArticleContentTextView.setHtml(StringEscapeUtils.unescapeHtml4(mArticle.getContent()), new HtmlHttpImageGetter(mArticleContentTextView));
        mTimeTextView.setText(mArticle.getDate());
        if (MyApplication.getmLoginStatus() == 1) {
            if (bean.isIfLike() == 0) {
                mLikeImageButton.setImageResource(R.mipmap.favourite);
            } else {
                mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
            }
        }
    }

    @Override
    public void hideProgress() {
        mLoadingViewStub.setVisibility(View.GONE);
    }

    @Override
    public void showLoadFailMsg(String msg) {
        SnackBarUtil.showSnackBar(this, msg);
    }

    @Override
    public void likeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite_tapped);
        bean.setIfLike(1);
        addArticle(bean);
        SnackBarUtil.showSnackBar(this, "收藏成功！");
    }

    @Override
    public void unlikeSuccess() {
        mLikeImageButton.setImageResource(R.mipmap.favourite);
        bean.setIfLike(0);
        addArticle(bean);
        SnackBarUtil.showSnackBar(this, "取消收藏成功！");
    }

    @OnClick(R.id.article_detail_back_ib)
    public void click_back() {
        finish();
    }

    @OnClick(R.id.article_detail_like_ib)
    public void click_like() {
        if (bean.isIfLike() == 0) {
            like(bean.getArticle_id());
        } else {
            unlike(bean.getArticle_id());
        }
    }

    public void like(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        if (account != "") {
            mPresenter.like(articleId, account);
            refresh();
        } else {
            showLoadFailMsg("请先登录!");
        }
    }

    public void unlike(String articleId) {
        String account = "";
        if (MyApplication.getmLoginStatus() == 1) {
            account = MyApplication.getAccount();
        }
        if (account != "") {
            mPresenter.unlike(articleId, account);
            refresh();
        } else {
            showLoadFailMsg("请先登录!");
        }
    }

    private void refresh() {
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mPresenter.loadArticle(cur_article_id);
    }
}
