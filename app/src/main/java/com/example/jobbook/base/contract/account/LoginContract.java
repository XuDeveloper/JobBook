package com.example.jobbook.base.contract.account;

import com.example.jobbook.base.IBasePresenter;
import com.example.jobbook.base.IBaseView;
import com.example.jobbook.model.bean.PersonBean;

/**
 * Created by Xu on 2017/12/17.
 */

public interface LoginContract {

    interface View extends IBaseView {
        void setAccountError();

        void setPasswordError();

        void switch2Person(PersonBean personBean);
    }

    interface Presenter extends IBasePresenter<View> {
        void loginCheck(String account, String password);
    }
}
