package com.example.jobbook.model.exception;

import android.util.SparseArray;

import com.example.jobbook.app.NetConstants;

/**
 * Created by zhaoxuzhang on 2017/11/4.
 * @author zhaoxuzhang
 */

public class ApiException extends RuntimeException {

    private static SparseArray<String> errors;

    static {
        errors = new SparseArray<>();
        errors.append(NetConstants.NETWORK_ERROR_CODE, NetConstants.NETWORK_ERROR_WORD);
        errors.append(NetConstants.PERSON_LOGIN_ERROR_CODE, NetConstants.PERSON_LOGIN_ERROR_WORD);
        errors.append(NetConstants.PERSON_AUTO_LOGIN_ERROR_CODE, NetConstants.PERSON_AUTO_LOGIN_ERROR_WORD);
        errors.append(NetConstants.PERSON_LOGIN_FIRST_ERROR_CODE, NetConstants.PERSON_LOGIN_FIRST_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_LIKE_ERROR_CODE, NetConstants.ARTICLE_LIKE_ERROR_WORD);
        errors.append(NetConstants.ARTICLE_UNLIKE_ERROR_CODE, NetConstants.ARTICLE_UNLIKE_ERROR_WORD);
        errors.append(NetConstants.CV_UPDATE_ERROR_CODE, NetConstants.CV_UPDATE_ERROR_WORD);
        errors.append(NetConstants.CV_GET_ERROR_CODE, NetConstants.CV_GET_ERROR_WORD);
        errors.append(NetConstants.PERSON_REGISTER_ERROR_CODE, NetConstants.PERSON_REGISTER_ERROR_WORD);
        errors.append(NetConstants.PERSON_REGISTER_ACCOUNT_EXISTS_CODE, NetConstants.PERSON_REGISTER_ACCOUNT_EXISTS_WORD);
        errors.append(NetConstants.JOB_LIKE_ERROR_CODE, NetConstants.JOB_LIKE_ERROR_WORD);
        errors.append(NetConstants.JOB_UNLIKE_ERROR_CODE, NetConstants.JOB_UNLIKE_ERROR_WORD);
        errors.append(NetConstants.PERSON_SEND_MAIL_NO_DESTINATION_ERROR_CODE, NetConstants.PERSON_SEND_MAIL_NO_DESTINATION_ERROR_WORD);
        errors.append(NetConstants.PERSON_SEND_MAIL_ERROR_CODE, NetConstants.PERSON_SEND_MAIL_ERROR_WORD);
        errors.append(NetConstants.PERSON_SEND_MAIL_NO_DATA_CODE, NetConstants.PERSON_SEND_MAIL_NO_DATA_WORD);
        errors.append(NetConstants.PERSON_SEND_MAIL_HAVE_SENT_CODE, NetConstants.PERSON_SEND_MAIL_HAVE_SENT_WORD);
        errors.append(NetConstants.PERSON_UPDATE_TEL_ERROR_CODE, NetConstants.PERSON_UPDATE_TEL_ERROR_WORD);
        errors.append(NetConstants.PERSON_UPDATE_PWD_ERROR_CODE, NetConstants.PERSON_UPDATE_PWD_ERROR_WORD);
        errors.append(NetConstants.PERSON_UPDATE_PWD_WRONG_PWD_ERROR_CODE, NetConstants.PERSON_UPDATE_PWD_WRONG_PWD_ERROR_WORD);
        errors.append(NetConstants.PERSON_UPDATE_NAME_ERROR_CODE, NetConstants.PERSON_UPDATE_NAME_ERROR_WORD);
        errors.append(NetConstants.PERSON_UPDATE_NAME_NAME_EXISTS_CODE, NetConstants.PERSON_UPDATE_NAME_NAME_EXISTS_WORD);
        errors.append(NetConstants.PERSON_FOCUS_ERROR_CODE, NetConstants.PERSON_FOCUS_ERROR_WORD);
        errors.append(NetConstants.PERSON_FOCUS_HAVED_FOCUSED_ERROR_CODE, NetConstants.PERSON_FOCUS_HAVED_FOCUSED_ERROR_WORD);
        errors.append(NetConstants.PERSON_UNFOCUS_ERROR_CODE, NetConstants.PERSON_UNFOCUS_ERROR_WORD);
        errors.append(NetConstants.PERSON_UPLOAD_HEAD_ERROR_CODE, NetConstants.PERSON_UPLOAD_HEAD_ERROR_WORD);
        errors.append(NetConstants.SQUARE_RELEASE_ERROR_CODE, NetConstants.SQUARE_RELEASE_ERROR_WORD);
        errors.append(NetConstants.SQUARE_LIKE_ERROR_CODE, NetConstants.SQUARE_LIKE_ERROR_WORD);
        errors.append(NetConstants.SQUARE_HAS_LIKED_ERROR_CODE, NetConstants.SQUARE_HAS_LIKED_ERROR_WORD);
        errors.append(NetConstants.SQUARE_UNLIKE_ERROR_CODE, NetConstants.SQUARE_UNLIKE_ERROR_WORD);

    }

    public ApiException(int code) {
        this(getApiExceptionMessage(code));
    }

    private ApiException(String message) {
        super(message);
    }

    /**
     * 需要根据错误码对错误信息进行一个转换
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        return errors.get(code);
    }
}
