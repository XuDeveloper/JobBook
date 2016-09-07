package com.example.jobbook.update.presenter;

import com.example.jobbook.update.model.UpdateModel;
import com.example.jobbook.update.model.UpdateModelImpl;
import com.example.jobbook.update.view.UpdateUsernameView;

/**
 * Created by Xu on 2016/9/5.
 */
public class UpdateUsernamePresenterImpl implements UpdateUsernamePresenter, UpdateModelImpl.OnUpdateUserNameListener {

    private UpdateModel model;
    private UpdateUsernameView view;

    public UpdateUsernamePresenterImpl(UpdateUsernameView view) {
        this.view = view;
        model = new UpdateModelImpl();
    }

    @Override
    public void update(String account, String username) {
        model.updateUserName(account, username, this);
    }

    @Override
    public void onUpdateUserNameSuccess() {
        view.success();
        view.close();
    }

    @Override
    public void onUpdateUserNameFailure() {
        view.networkError();
    }

    @Override
    public void onUserNameBlankError() {
        view.usernameBlankError();
    }
}
