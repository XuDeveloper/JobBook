package com.example.jobbook.base.contract.person;

import android.content.Context;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;

/**
 * Created by Xu on 2017/12/17.
 */

public interface UpdateContract {

    interface UpdatePhoneView extends IBaseView {

        void codeBlankError();

        void codeError();

        void newPhoneBlankError();

        void success();
    }

    interface UpdatePhonePresenter extends IBasePresenter<UpdatePhoneView> {
        void complete(Context mContext, String account, String tel, String code);
    }

    interface UpdatePwdView extends IBaseView {
        void oPwdBlankError();

        void nPwdBlankError();

        void nPwdConfirmBlankError();

        void pwdConfirmError();

        void success();

        void oPwdError();

        void oPwdEqualnPwdError();
    }

    interface UpdatePwdPresenter extends IBasePresenter<UpdatePwdView> {
        void updatePwd(Context context, String account, String oPwd, String nPwd, String nPwdConfirm);
    }

    interface UpdateUsernameView extends IBaseView {
        void usernameBlankError();

        void success();
    }

    interface UpdateUsernamePresenter extends IBasePresenter<UpdateUsernameView> {
        void update(Context context, String account, String username);
    }

}
