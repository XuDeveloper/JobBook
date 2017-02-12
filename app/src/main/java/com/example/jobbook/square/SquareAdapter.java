package com.example.jobbook.square;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.MomentBean;
import com.example.jobbook.commons.Urls;
import com.example.jobbook.util.ImageLoadUtils;
import com.example.jobbook.util.L;

import java.util.List;

/**
 * Created by 椰树 on 2016/8/30.
 */
public class SquareAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ITEM_OVER = 1;
    private static final int TYPE_FOOTER = 2;

    private List<MomentBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnContentClickListener mOnContentClickListener;
    private OnHeadClickListener mOnHeadClickListener;
    private OnNoInterestButtonClickListener mOnNoInterestButtonClickListener;
    private OnFavouriteButtonClickListener mOnFavouriteButtonClickListener;
    private OnShowAllOrHideAllListener mOnShowAllOrHideAllListener;

    public SquareAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.square_rv_item, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else if (viewType == TYPE_ITEM_OVER) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.square_recycleview_item_oversize, parent, false);
            ItemOverSizeViewHolder vh = new ItemOverSizeViewHolder(v);
            return vh;
        }
        else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.loadingfooter_layout, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            MomentBean moment = mData.get(position);
            ((ItemViewHolder) holder).mContent.setText(moment.getContent());
            ImageLoadUtils.display(mContext, ((ItemViewHolder) holder).mUserHead, moment.getAuthor().getHead());
            ((ItemViewHolder) holder).mUserName.setText(moment.getAuthor().getUsername());
            ((ItemViewHolder) holder).mFavouriteNumbers.setText(moment.getLikesNum() + "");
            ((ItemViewHolder) holder).mCommentNumbers.setText(moment.getCommentNum() + "");
            ((ItemViewHolder) holder).mTime.setText(moment.getDate());
            ((ItemViewHolder) holder).mCompanyAndPositionTextView.setText(moment.getAuthor().getWorkSpace() + " "
                                                + moment.getAuthor().getWorkPosition());
            if (MyApplication.getAccount() != null) {
                if(MyApplication.getAccount().equals(moment.getAuthor().getAccount())){
                    ((ItemViewHolder)holder).mNoInterestButton.setVisibility(View.GONE);
                }
            } else {
                ((ItemViewHolder)holder).mNoInterestButton.setVisibility(View.GONE);
            }
            if (moment.getIfLike() == 0) {
                ((ItemViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite);
            } else {
                ((ItemViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite_tapped);
            }
        } else if (holder instanceof ItemOverSizeViewHolder) {
            final MomentBean moment = mData.get(position);
            ((ItemOverSizeViewHolder) holder).mContent.setText(moment.getContent().substring(0, 75));
            ImageLoadUtils.display(mContext, ((ItemOverSizeViewHolder) holder).mUserHead, moment.getAuthor().getHead());
            ((ItemOverSizeViewHolder) holder).mUserName.setText(moment.getAuthor().getUsername());
            ((ItemOverSizeViewHolder) holder).mFavouriteNumbers.setText(moment.getLikesNum() + "");
            ((ItemOverSizeViewHolder) holder).mCommentNumbers.setText(moment.getCommentNum() + "");
            ((ItemOverSizeViewHolder) holder).mTime.setText(moment.getDate());
            ((ItemOverSizeViewHolder) holder).mCompanyAndPositionTextView.setText(moment.getAuthor().getWorkSpace() + " "
                    + moment.getAuthor().getWorkPosition());
            if (MyApplication.getAccount() != null) {
                if(MyApplication.getAccount().equals(moment.getAuthor().getAccount())){
                    ((ItemOverSizeViewHolder)holder).mNoInterestButton.setVisibility(View.GONE);
                }
            } else {
                ((ItemOverSizeViewHolder)holder).mNoInterestButton.setVisibility(View.GONE);
            }
            if (moment.getIfLike() == 0) {
                ((ItemOverSizeViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite);
            } else {
                ((ItemOverSizeViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite_tapped);
            }
            if (mOnShowAllOrHideAllListener != null) {
                ((ItemOverSizeViewHolder) holder).mShowAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        mOnShowAllOrHideAllListener.onShowAllOrHideAll(textView, ((ItemOverSizeViewHolder) holder).mContent, moment.getContent());
                    }
                });
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder , int position , List playloads){
        if(playloads.isEmpty()){
            onBindViewHolder(holder, position);
        }else if (holder instanceof ItemViewHolder){
            String playload = (String)playloads.get(0);
            L.i("playload", playload);
            MomentBean moment = mData.get(position);
            ((ItemViewHolder) holder).mFavouriteNumbers.setText(moment.getLikesNum() + "");
            ((ItemViewHolder) holder).mCommentNumbers.setText(moment.getCommentNum() + "");
            if (moment.getIfLike() == 0) {
                ((ItemViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite);
            } else {
                ((ItemViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite_tapped);
            }
//            viewHolder.mFavouriteNumbers.setText("1");
        }else if (holder instanceof ItemOverSizeViewHolder) {
            String playload = (String)playloads.get(0);
            L.i("playload", playload);
            MomentBean moment = mData.get(position);
            ((ItemOverSizeViewHolder) holder).mFavouriteNumbers.setText(moment.getLikesNum() + "");
            ((ItemOverSizeViewHolder) holder).mCommentNumbers.setText(moment.getCommentNum() + "");
            if (moment.getIfLike() == 0) {
                ((ItemOverSizeViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite);
            } else {
                ((ItemOverSizeViewHolder) holder).mFavouriteButton.setImageResource(R.mipmap.favourite_tapped);
            }
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            if (mData.get(position).getContent().length() > 75) {
                return TYPE_ITEM_OVER;
            } else {
                return TYPE_ITEM;
            }
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            if (mData.get(position).getContent().length() > 75) {
                return TYPE_ITEM_OVER;
            } else {
                return TYPE_ITEM;
            }
        }
        // 最后一个item设置为footerView
//        if(!mShowFooter) {
//            return TYPE_ITEM;
//        }
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mContent;
        ImageView mUserHead;
        TextView mUserName;
        TextView mFavouriteNumbers;
        TextView mCommentNumbers;
        TextView mTime;
        ImageButton mNoInterestButton;
        ImageButton mFavouriteButton;
        TextView mCompanyAndPositionTextView;

        public ItemViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.square_rv_content_tv);
            mUserHead = (ImageView) view.findViewById(R.id.square_rv_head_iv);
            mUserName = (TextView) view.findViewById(R.id.square_rv_username_tv);
            mFavouriteNumbers = (TextView) view.findViewById(R.id.square_rv_favourite_tv);
            mCommentNumbers = (TextView) view.findViewById(R.id.square_rv_comment_tv);
            mTime = (TextView) view.findViewById(R.id.square_rv_time_tv);
            mNoInterestButton = (ImageButton) view.findViewById(R.id.square_rv_no_interest_ib);
            mFavouriteButton = (ImageButton) view.findViewById(R.id.square_rv_favourite_ib);
            mCompanyAndPositionTextView = (TextView) view.findViewById(R.id.square_rv_company_position_tv);
            mContent.setOnClickListener(this);
            mUserHead.setOnClickListener(this);
            mNoInterestButton.setOnClickListener(this);
            mFavouriteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.square_rv_no_interest_ib:
                    if (mOnNoInterestButtonClickListener != null) {
                        mOnNoInterestButtonClickListener.onNoInterestButtonClick(v, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_rv_head_iv:
                    if (mOnHeadClickListener != null) {
                        mOnHeadClickListener.onHeadClick(v, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_rv_favourite_ib:
                    if (mOnFavouriteButtonClickListener != null) {
                        ImageButton ib = (ImageButton) v;
                        mOnFavouriteButtonClickListener.onFavouriteButtonClick(ib, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_rv_content_tv:
                    if(mOnContentClickListener != null){
                        mOnContentClickListener.onContentClick(v, this.getAdapterPosition());
                    }
                    break;
            }
        }
    }

    public class ItemOverSizeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mContent;
        ImageView mUserHead;
        TextView mUserName;
        TextView mFavouriteNumbers;
        TextView mCommentNumbers;
        TextView mTime;
        ImageButton mNoInterestButton;
        ImageButton mFavouriteButton;
        TextView mCompanyAndPositionTextView;
        TextView mShowAll;

        public ItemOverSizeViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.square_oversize_rv_content_tv);
            mUserHead = (ImageView) view.findViewById(R.id.square_oversize_rv_head_iv);
            mUserName = (TextView) view.findViewById(R.id.square_oversize_rv_username_tv);
            mFavouriteNumbers = (TextView) view.findViewById(R.id.square_oversize_rv_favourite_tv);
            mCommentNumbers = (TextView) view.findViewById(R.id.square_oversize_rv_comment_tv);
            mTime = (TextView) view.findViewById(R.id.square_oversize_rv_time_tv);
            mNoInterestButton = (ImageButton) view.findViewById(R.id.square_oversize_rv_no_interest_ib);
            mFavouriteButton = (ImageButton) view.findViewById(R.id.square_oversize_rv_favourite_ib);
            mCompanyAndPositionTextView = (TextView) view.findViewById(R.id.square_oversize_rv_company_position_tv);
            mShowAll = (TextView) view.findViewById(R.id.square_oversize_rv_show_all_tv);
            mContent.setOnClickListener(this);
            mUserHead.setOnClickListener(this);
            mNoInterestButton.setOnClickListener(this);
            mFavouriteButton.setOnClickListener(this);
//            mShowAll.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.square_oversize_rv_no_interest_ib:
                    if (mOnNoInterestButtonClickListener != null) {
                        mOnNoInterestButtonClickListener.onNoInterestButtonClick(v, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_oversize_rv_head_iv:
                    if (mOnHeadClickListener != null) {
                        mOnHeadClickListener.onHeadClick(v, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_oversize_rv_favourite_ib:
                    if (mOnFavouriteButtonClickListener != null) {
                        ImageButton ib = (ImageButton) v;
                        mOnFavouriteButtonClickListener.onFavouriteButtonClick(ib, this.getAdapterPosition());
                    }
                    break;

                case R.id.square_oversize_rv_content_tv:
                    if(mOnContentClickListener != null){
                        mOnContentClickListener.onContentClick(v, this.getAdapterPosition());
                    }
                    break;
            }
        }
    }

    public void updateData(List<MomentBean> mData) {
        this.mData = mData;
        if (mData.size() % Urls.PAZE_SIZE != 0) {
            this.setmShowFooter(false);
        }
        this.notifyDataSetChanged();
    }

    public List<MomentBean> getmData() {
        return mData;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnHeadClickListener {
        void onHeadClick(View view, int position);
    }

    public interface OnNoInterestButtonClickListener {
        void onNoInterestButtonClick(View view, int position);
    }

    public interface OnFavouriteButtonClickListener {
        void onFavouriteButtonClick(ImageButton ib, int position);
    }

    public interface OnContentClickListener{
        void onContentClick(View view, int position);
    }

    public interface OnShowAllOrHideAllListener {
        void onShowAllOrHideAll(TextView view, TextView contentTextView, String content);
    }

    public void setOnContentClickListener(OnContentClickListener onContentClickListener){
        this.mOnContentClickListener = onContentClickListener;
    }

    public void setOnHeadClickListener(OnHeadClickListener onHeadClickListener) {
        this.mOnHeadClickListener = onHeadClickListener;
    }

    public void setOnNoInterestButtonClickListener(OnNoInterestButtonClickListener onNoInterestButtonClickListener) {
        this.mOnNoInterestButtonClickListener = onNoInterestButtonClickListener;
    }

    public void setOnFavouriteButtonClickListener(OnFavouriteButtonClickListener onFavouriteButtonClickListener) {
        this.mOnFavouriteButtonClickListener = onFavouriteButtonClickListener;
    }

    public void setOnShowAllOrHideAllListener(OnShowAllOrHideAllListener onShowAllOrHideAllListener) {
        this.mOnShowAllOrHideAllListener = onShowAllOrHideAllListener;
    }

    public MomentBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }
}
