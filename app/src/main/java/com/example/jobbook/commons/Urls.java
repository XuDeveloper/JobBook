package com.example.jobbook.commons;

/**
 * Created by Xu on 2016/8/25.
 */
public class Urls {

    public static final String ID = "http://192.168.1.139";

    public static final String REGISTER_URL = ID + "/jobBook/index.php/Home/enter/doRegister";

    public static final String LOGIN_URL = ID + "/jobBook/Home/enter/doLogin";

    public static final String JOB_URL = ID + "/jobBook/Home/job/getAll";

    public static final String JOB_DETAIL_URL = ID + "/jobBook/Home/job/getDetail/job_id/";

    public static final String JOB_LIKE_URL = ID + "/jobBook/Home/job/liked/job_id/";

    public static final String JOB_UNLIKE_URL = ID + "/jobBook/Home/job/unliked/job_id/";

    public static final String ARTICLE_URL = ID + "/jobBook/Home/article/allArticle/type/";

    public static final String ARTICLE_DETAIL_URL = ID + "/jobBook/Home/article/getArticle/a_id/";



}
