package com.example.jobbook.commons;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final int PAZE_SIZE = 10;

    public static final String ID = "http://192.168.8.28";

    public static final String REGISTER_URL = ID + "/jobBook/Home/enter/doRegister/";

    public static final String LOGIN_URL = ID + "/jobBook/Home/enter/doLogin";

    public static final String JOB_URL = ID + "/jobBook/Home/job/getAll";

    public static final String JOB_DETAIL_URL = ID + "/jobBook/Home/job/getDetail/job_id/";

    public static final String JOB_LIKE_URL = ID + "/jobBook/Home/job/liked/job_id/";

    public static final String JOB_UNLIKE_URL = ID + "/jobBook/Home/job/unliked/job_id/";

    public static final String ARTICLE_URL = ID + "/jobBook/Home/article/allArticle/type/";

    public static final String ARTICLE_DETAIL_URL = ID + "/jobBook/Home/article/getArticle/a_id/";

    public static final String QUESTION_URL = ID + "/jobBook/Home/question/allQuestions";

    public static final String QUESTION_DETAIL_URL = ID + "/jobBook/Home/question/getQuestion/q_id/";

    public static final String NEW_QUESTION_URL = ID + "/jobBook/Home/question/releaseQuestion";

    public static final String SEND_QUESTION_COMMENT_URL = ID + "/jobBook/Home/question/comment";

    public static final String SEARCH_URL = ID + "/jobBook/Home/job/search/keyword/";

    public static final String GET_CODE_URL = ID + "/jobBook/Home/enter/verify";

    public static final String FEED_BACK_URL = ID + "/jobBook/Home/suggestion/postSuggestion/account/";

    public static final String FAVOURITE_URL = ID + "/jobBook/Home/person/MyFavourite/account/";

    public static final String POST_TEXT_CV_URL = ID + "/jobBook/Home/cv/postCV/account/";

    public static final String LOAD_TEXT_CV_URL = ID + "/jobBook/Home/cv/getcv/account/";

    public static final String UPLOAD_IMAGE_URL = ID + "/jobBook/Home/person/upload2/";

    public static final String UPDATE_PHONE_URL = ID + "/jobBook/Home/person/updateTel/";

    public static final String UPDATE_PWD_URL = ID + "/jobBook/Home/person/updatepsw/";

    public static final String UPDATE_USERNAME_URL = ID + "/jobBook/Home/person/updateName/";

    public static final String SEND_CV_URL = ID + "/jobBook/Home/mail/check/";

}
