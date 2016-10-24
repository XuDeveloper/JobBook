package com.example.jobbook.cv.widget;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jobbook.MyApplication;
import com.example.jobbook.R;
import com.example.jobbook.bean.EducationExpBean;
import com.example.jobbook.bean.JobExpBean;
import com.example.jobbook.bean.TextCVBean;
import com.example.jobbook.cv.presenter.TextCVPresenter;
import com.example.jobbook.cv.presenter.TextCVPresenterImpl;
import com.example.jobbook.cv.view.TextCVView;
import com.example.jobbook.util.L;
import com.example.jobbook.util.Util;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;
import com.jzxiang.pickerview.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 椰树 on 2016/8/25.
 */
public class TextCVActivity extends AppCompatActivity implements OnDateSetListener, View.OnClickListener, TextCVView {
    private static final String EDU_ADMISSION = "1";
    private static final String EDU_GRADUATION = "2";
    private static final String JOB_INAUGURATION = "3";
    private static final String JOB_DIMISSION = "4";
    private TimePickerDialog mJobExpInaugurationDialog;
    private TimePickerDialog mJobExpDimissionDialog;
    private TimePickerDialog mEduExpAdmissionDialog;
    private TimePickerDialog mEduExpGraduationDialog;
    private TextView mJobExpInaugurationTextView;
    private TextView mJobExpDimissionTextView;
    private TextView mEduExpAdmissionTextView;
    private TextView mEduExpGraduationTextView;
    private LinearLayout mEduContainerLayout;
    private LinearLayout mJobContainerLayout;
    private ImageButton mAddEduExpImageButton;
    private ImageButton mAddJobExpImageButton;
    private ImageButton mCloseImageButton;
    private TextView mSaveTextView;
    private EditText mNameEditText;
    private Spinner mSexSpinner;
    private EditText mQualificationEditText;
    private EditText mCityEditText;
    private EditText mTypeEditText;
    private EditText mLevelEditText;
    private Spinner mCertificationSpinner;
    private EditText mTelEditText;
    private EditText mEmailEditText;
    private EditText mExpectJobEditText;
    private EditText mExpectSalaryEditText;
    private EditText mExpectLocationEditText;
    private TextView mEduDivider;
    private TextView mJobDivider;
    private LinearLayout mLoadingLayout;
    private TextCVPresenter mPresenter;
    

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_cv);
        initView();
        initEvents();
    }

    private void initView() {
        mNameEditText = (EditText) findViewById(R.id.text_cv_name_et);
        mSexSpinner = (Spinner) findViewById(R.id.text_cv_sex_spinner);
        mQualificationEditText = (EditText) findViewById(R.id.text_cv_qualification_et);
        mCityEditText = (EditText) findViewById(R.id.text_cv_location_et);
        mTypeEditText = (EditText) findViewById(R.id.text_cv_type_et);
        mLevelEditText = (EditText) findViewById(R.id.text_cv_level_et);
        mCertificationSpinner = (Spinner) findViewById(R.id.text_cv_certificate_spinner);
        mTelEditText = (EditText) findViewById(R.id.text_cv_tel_et);
        mEmailEditText = (EditText) findViewById(R.id.text_cv_email_et);
        mExpectJobEditText = (EditText) findViewById(R.id.text_cv_expect_job_positon_et);
        mExpectSalaryEditText = (EditText) findViewById(R.id.text_cv_expect_job_salary_et);
        mExpectLocationEditText = (EditText) findViewById(R.id.text_cv_expect_job_location_et);
        mJobExpInaugurationTextView = (TextView) findViewById(R.id.job_exp_inauguration_tv);
        mJobExpDimissionTextView = (TextView) findViewById(R.id.job_exp_dimission_tv);
        mEduExpAdmissionTextView = (TextView) findViewById(R.id.education_exp_admission_tv);
        mEduExpGraduationTextView = (TextView) findViewById(R.id.education_exp_graduation_tv);
        mEduContainerLayout = (LinearLayout) findViewById(R.id.text_cv_edu_container);
        mJobContainerLayout = (LinearLayout) findViewById(R.id.text_cv_job_container);
        mAddEduExpImageButton = (ImageButton) findViewById(R.id.text_cv_add_edu_exp_ib);
        mAddJobExpImageButton = (ImageButton) findViewById(R.id.text_cv_add_job_exp_ib);
        mCloseImageButton = (ImageButton) findViewById(R.id.text_cv_close_ib);
        mSaveTextView = (TextView) findViewById(R.id.text_cv_save_tv);
        mEduDivider = (TextView) findViewById(R.id.edu_exp_divider);
        mJobDivider = (TextView) findViewById(R.id.job_exp_divider);
        mLoadingLayout = (LinearLayout) findViewById(R.id.loading_circle_progress_bar_ll);
    }

    private void initEvents() {
        mPresenter = new TextCVPresenterImpl(this);
        mPresenter.load();
        mJobExpInaugurationTextView.setOnClickListener(this);
        mJobExpDimissionTextView.setOnClickListener(this);
        mEduExpAdmissionTextView.setOnClickListener(this);
        mEduExpGraduationTextView.setOnClickListener(this);
        mAddEduExpImageButton.setOnClickListener(this);
        mAddJobExpImageButton.setOnClickListener(this);
        mCloseImageButton.setOnClickListener(this);
        mSaveTextView.setOnClickListener(this);
        mJobExpInaugurationTextView.setTag(0);
        mJobExpDimissionTextView.setTag(0);
        mEduExpAdmissionTextView.setTag(0);
        mEduExpGraduationTextView.setTag(0);
        mJobExpInaugurationDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mJobExpDimissionDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mEduExpAdmissionDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();
        mEduExpGraduationDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH)
                .setTitleStringId("选择时间")
                .setThemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setCallBack(this)
                .build();

    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        Date d = new Date(millseconds);
        String text = sf.format(d);
        String tag = timePickerView.getTag();
        if (tag.startsWith(JOB_DIMISSION)) {
            View view = mJobContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mJobExpDimissionTextView = (TextView) view.findViewById(R.id.job_exp_dimission_tv);
            mJobExpDimissionTextView.setText(text);
        } else if (tag.startsWith(JOB_INAUGURATION)) {
            View view = mJobContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mJobExpInaugurationTextView = (TextView) view.findViewById(R.id.job_exp_inauguration_tv);
            mJobExpInaugurationTextView.setText(text);
        } else if (tag.startsWith(EDU_ADMISSION)) {
            View view = mEduContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mEduExpAdmissionTextView = (TextView) view.findViewById(R.id.education_exp_admission_tv);
            mEduExpAdmissionTextView.setText(text);
        } else {
            View view = mEduContainerLayout.getChildAt(Integer.valueOf(tag.split(";")[1]));
            TextView mEduExpGraduationTextView = (TextView) view.findViewById(R.id.education_exp_graduation_tv);
            mEduExpGraduationTextView.setText(text);
        }
    }

    private View addJobView(LinearLayout mJobContainerLayout){
        LinearLayout jobView = (LinearLayout) View.inflate(this, R.layout.job_experiment_layout, null);
        View jobDeleteView = View.inflate(this, R.layout.delete_exp_layout, null);
        LinearLayout.LayoutParams jobParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        jobView.addView(jobDeleteView, jobParams);
        mJobContainerLayout.addView(jobView, jobParams);
        return jobView;
    }

    private void initJobView(View jobView){
        TextView mInaugurationTextView = (TextView) jobView.findViewById(R.id.job_exp_inauguration_tv);
        TextView mDimissionTextView = (TextView) jobView.findViewById(R.id.job_exp_dimission_tv);
        EditText mCompantEditText = (EditText) jobView.findViewById(R.id.job_exp_company_et);
        EditText mPositionEditText = (EditText) jobView.findViewById(R.id.job_exp_position_et);
        mInaugurationTextView.setTag(mJobContainerLayout.getChildCount() - 1);
        mDimissionTextView.setTag(mJobContainerLayout.getChildCount() - 1);
        mInaugurationTextView.setOnClickListener(this);
        mDimissionTextView.setOnClickListener(this);
    }
    private void initJobView(View jobView, JobExpBean jobExpBean){
        initJobView(jobView);
        ((TextView)jobView.findViewById(R.id.job_exp_inauguration_tv)).setText(jobExpBean.getInaugurationDate());
        ((TextView)jobView.findViewById(R.id.job_exp_dimission_tv)).setText(jobExpBean.getDimissionDate());
        ((EditText)jobView.findViewById(R.id.job_exp_company_et)).setText(jobExpBean.getCompany());
        ((EditText)jobView.findViewById(R.id.job_exp_position_et)).setText(jobExpBean.getPosition());
    }

    private View addEduView(LinearLayout mEduContainerLayout){
        LinearLayout eduView = (LinearLayout) View.inflate(this, R.layout.education_experiment_layout, null);
        View eduDeleteView = View.inflate(this, R.layout.delete_exp_layout, null);
        LinearLayout.LayoutParams eduParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        eduView.addView(eduDeleteView, eduParams);
        mEduContainerLayout.addView(eduView, eduParams);
        return eduView;
    }

    private void initEduView(View eduView){
        TextView mAdmissionTextView = (TextView) eduView.findViewById(R.id.education_exp_admission_tv);
        TextView mGraduationTextView = (TextView) eduView.findViewById(R.id.education_exp_graduation_tv);
        EditText mSchoolEditText = (EditText) eduView.findViewById(R.id.education_school_et);
        EditText mMajorEditText = (EditText) eduView.findViewById(R.id.education_major_et);
        mAdmissionTextView.setTag(mEduContainerLayout.getChildCount() - 1);
        mGraduationTextView.setTag(mEduContainerLayout.getChildCount() - 1);
        mAdmissionTextView.setOnClickListener(this);
        mGraduationTextView.setOnClickListener(this);
    }

    private void initEduView(View eduView, EducationExpBean educationExpBean){
        initEduView(eduView);
        ((TextView)eduView.findViewById(R.id.education_exp_admission_tv)).setText(educationExpBean.getAdmissionDate());
        ((TextView)eduView.findViewById(R.id.education_exp_graduation_tv)).setText(educationExpBean.getGraduationDate());
        ((EditText)eduView.findViewById(R.id.education_school_et)).setText(educationExpBean.getSchool());
        ((EditText)eduView.findViewById(R.id.education_major_et)).setText(educationExpBean.getMajor());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.job_exp_inauguration_tv:
                mJobExpInaugurationDialog.show(getSupportFragmentManager(), JOB_INAUGURATION + ";" + (int) v.getTag());
                mJobDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
                break;
            case R.id.job_exp_dimission_tv:
                mJobExpDimissionDialog.show(getSupportFragmentManager(), JOB_DIMISSION + ";" + (int) v.getTag());
                mJobDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
                break;
            case R.id.education_exp_admission_tv:
                mEduExpAdmissionDialog.show(getSupportFragmentManager(), EDU_ADMISSION + ";" + (int) v.getTag());
                mEduDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
                break;
            case R.id.education_exp_graduation_tv:
                mEduExpGraduationDialog.show(getSupportFragmentManager(), EDU_GRADUATION + ";" + (int) v.getTag());
                mEduDivider.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorBackgroundGray));
                break;
            case R.id.text_cv_close_ib:
                close();
                break;
            case R.id.text_cv_save_tv:
                save();
                break;
            case R.id.text_cv_add_edu_exp_ib:
                View eduView = addEduView(mEduContainerLayout);
                initEduView(eduView);
                ImageButton mEduDeleteImageButton = (ImageButton) eduView.findViewById(R.id.delete_exp_ib);
                mEduDeleteImageButton.setTag(EDU_ADMISSION + ";" + (mEduContainerLayout.getChildCount() - 1));
                mEduDeleteImageButton.setOnClickListener(this);
                break;
            case R.id.text_cv_add_job_exp_ib:
                View jobView = addJobView(mJobContainerLayout);
                initJobView(jobView);
                ImageButton mJobDeleteImageButton = (ImageButton) jobView.findViewById(R.id.delete_exp_ib);
                mJobDeleteImageButton.setTag(JOB_INAUGURATION + ";" + (mJobContainerLayout.getChildCount() - 1));
                mJobDeleteImageButton.setOnClickListener(this);
                break;
            case R.id.delete_exp_ib:
                String position = (String)v.getTag();
                if(position.startsWith(JOB_INAUGURATION)){
                    mJobContainerLayout.removeViewAt(Integer.valueOf(position.split(";")[1]));
                }else{
                    mEduContainerLayout.removeViewAt(Integer.valueOf(position.split(";")[1]));
                }
                break;

        }
    }

    @Override
    public void success() {
        MyApplication myApplication = (MyApplication) getApplication();
        Handler handler = myApplication.getHandler();
        handler.sendEmptyMessage(1);
        close();
    }

    @Override
    public void networkError() {
        Util.showSnackBar(this, "网络错误！");
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
    public void headBlankError() {

    }

    @Override
    public void nameBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_name_tv);
        setBackgroundRed(mNameEditText, textView);
    }

    @Override
    public void qualificationBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_qualification_tv);
        setBackgroundRed(mQualificationEditText, textView);
    }

    @Override
    public void locationBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_location_tv);
        setBackgroundRed(mCityEditText, textView);
    }

    @Override
    public void levelBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_level_tv);
        setBackgroundRed(mLevelEditText, textView);
    }

    @Override
    public void typeBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_type_tv);
        setBackgroundRed(mTypeEditText, textView);
    }

    @Override
    public void telBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_tel_tv);
        setBackgroundRed(mTelEditText, textView);
    }

    @Override
    public void emailBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_email_tv);
        setBackgroundRed(mEmailEditText, textView);
    }

    @Override
    public void eduExpAdmissionError(int id) {
        L.i("TextCV", "eduExpAdmissionError/" + id);
        View view = mEduContainerLayout.getChildAt(id);
        mEduDivider = (TextView) view.findViewById(R.id.edu_exp_divider);
        mEduDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void eduExpGraduationError(int id) {
        View view = mEduContainerLayout.getChildAt(id);
        mEduDivider = (TextView) view.findViewById(R.id.edu_exp_divider);
        mEduDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void eduExpSchoolBlankError(int id) {
        View view = mEduContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.education_school_et);
        TextView textView = (TextView) view.findViewById(R.id.education_school_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void eduExpMajorBlankError(int id) {

    }

    @Override
    public void jobExpInaugurationBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        mJobDivider = (TextView) view.findViewById(R.id.job_exp_divider);
        mJobDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void jobExpDimissionBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        mJobDivider = (TextView) view.findViewById(R.id.job_exp_divider);
        mJobDivider.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPink));
    }

    @Override
    public void jobExpCompanyBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.job_exp_company_et);
        TextView textView = (TextView) view.findViewById(R.id.job_exp_company_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void jobExpPositionBlankError(int id) {
        View view = mJobContainerLayout.getChildAt(id);
        EditText editText = (EditText) view.findViewById(R.id.job_exp_position_et);
        TextView textView = (TextView) view.findViewById(R.id.job_exp_position_tv);
        setBackgroundRed(editText, textView);
    }

    @Override
    public void expectJobPositionBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_positon_tv);
        setBackgroundRed(mExpectJobEditText, textView);
    }

    @Override
    public void expectJobSalaryBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_salary_tv);
        setBackgroundRed(mExpectSalaryEditText, textView);
    }

    @Override
    public void expectJobLocationBlankError() {
        TextView textView = (TextView) findViewById(R.id.text_cv_expect_job_location_tv);
        setBackgroundRed(mExpectLocationEditText, textView);
    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public void save() {
        mPresenter.basedInformationCheck("aaa", mNameEditText.getText().toString(), mSexSpinner.getSelectedItem().toString(),
                mQualificationEditText.getText().toString(), mCityEditText.getText().toString(), mTypeEditText.getText().toString(),
                mLevelEditText.getText().toString(), mCertificationSpinner.getSelectedItem().toString(),
                mTelEditText.getText().toString(), mEmailEditText.getText().toString(), mExpectJobEditText.getText().toString(),
                mExpectSalaryEditText.getText().toString(), mExpectLocationEditText.getText().toString());
        List<EducationExpBean> educationExpBeanList = new ArrayList<>();
        List<JobExpBean> jobExpBeanList = new ArrayList<>();
        for (int i = 0; i < mEduContainerLayout.getChildCount(); i++) {
            View view = mEduContainerLayout.getChildAt(i);
            TextView mAdmissionTextView = (TextView) view.findViewById(R.id.education_exp_admission_tv);
            TextView mGraduationTextView = (TextView) view.findViewById(R.id.education_exp_graduation_tv);
            EditText mSchoolEditText = (EditText) view.findViewById(R.id.education_school_et);
            EditText mMajorEditText = (EditText) view.findViewById(R.id.education_major_et);
            EducationExpBean educationExpBean = new EducationExpBean();
            educationExpBean.setAdmissionDate(mAdmissionTextView.getText().toString());
            educationExpBean.setGraduationDate(mGraduationTextView.getText().toString());
            educationExpBean.setSchool(mSchoolEditText.getText().toString());
            educationExpBean.setMajor(mMajorEditText.getText().toString());
            educationExpBeanList.add(educationExpBean);
        }
        for (int i = 0; i < mJobContainerLayout.getChildCount(); i++) {
            View view = mJobContainerLayout.getChildAt(i);
            TextView mInaugurationTextView = (TextView) view.findViewById(R.id.job_exp_inauguration_tv);
            TextView mDimissionTextView = (TextView) view.findViewById(R.id.job_exp_dimission_tv);
            EditText mCompanyEditText = (EditText) view.findViewById(R.id.job_exp_company_et);
            EditText mPositionEditText = (EditText) view.findViewById(R.id.job_exp_position_et);
            JobExpBean jobExpBean = new JobExpBean();
            jobExpBean.setInaugurationDate(mInaugurationTextView.getText().toString());
            jobExpBean.setDimissionDate(mDimissionTextView.getText().toString());
            jobExpBean.setCompany(mCompanyEditText.getText().toString());
            jobExpBean.setPosition(mPositionEditText.getText().toString());
            jobExpBeanList.add(jobExpBean);
        }
        mPresenter.educationExpCheck(educationExpBeanList);
        mPresenter.jobExpCheck(jobExpBeanList);
    }

    @Override
    public void load(TextCVBean textCVBean) {
        mNameEditText.setText(textCVBean.getName());
        String[] sex = getResources().getStringArray(R.array.sex);
        for (int i = 0; i < sex.length; i++) {
            String str = sex[i];
            if (textCVBean.getSex().equals(str)) {
                mSexSpinner.setSelection(i);
                break;
            }
        }
        mQualificationEditText.setText(textCVBean.getQualification());
        mCityEditText.setText(textCVBean.getCity());
        mTypeEditText.setText(textCVBean.getDisabilityType());
        mLevelEditText.setText(textCVBean.getDisabilityLevel());
        String[] ifHave = getResources().getStringArray(R.array.ifhave);
        for (int i = 0; i < ifHave.length; i++) {
            String str = ifHave[i];
            if (textCVBean.isHaveDisabilityCard().equals(str)) {
                mCertificationSpinner.setSelection(i);
                break;
            }
        }
        mTelEditText.setText(textCVBean.getTelephone());
        mEmailEditText.setText(textCVBean.getEmail());
        mExpectJobEditText.setText(textCVBean.getExpectPosition());
        mExpectSalaryEditText.setText(textCVBean.getExpectSalary());
        mExpectLocationEditText.setText(textCVBean.getExpectLocation());
        for (int i = 0; i < textCVBean.getEducationExpBeanList().size(); i++) {
            if(i != 0){
                View view = addEduView(mEduContainerLayout);
                ImageButton mEduDeleteImageButton = (ImageButton) view.findViewById(R.id.delete_exp_ib);
                mEduDeleteImageButton.setTag(EDU_ADMISSION + ";" + (mEduContainerLayout.getChildCount() - 1));
                mEduDeleteImageButton.setOnClickListener(this);
            }
            View view = mEduContainerLayout.getChildAt(i);
            EducationExpBean educationExpBean = textCVBean.getEducationExpBeanList().get(i);
            initEduView(view, educationExpBean);
        }
        for (int i = 0; i < textCVBean.getJobExpBeanList().size(); i++) {
            if(i != 0){
                View view = addJobView(mJobContainerLayout);
                ImageButton mJobDeleteImageButton = (ImageButton) view.findViewById(R.id.delete_exp_ib);
                mJobDeleteImageButton.setTag(JOB_INAUGURATION + ";" + (mJobContainerLayout.getChildCount() - 1));
                mJobDeleteImageButton.setOnClickListener(this);
            }
            View view = mJobContainerLayout.getChildAt(i);
            JobExpBean jobExpBean = textCVBean.getJobExpBeanList().get(i);
            initJobView(view, jobExpBean);
        }
    }

    private void setBackgroundRed(final EditText editText, final TextView textView) {
        if (editText == null) {
            L.i("TextCV", "arguments error!");
        } else {
//            editText.setBackground(ContextCompat.getDrawable(this, R.drawable.text_cv_et_red_bg));
            editText.setBackgroundResource(R.drawable.text_cv_et_red_bg);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPink));
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.
                    getDrawable(this, R.drawable.text_cv_vertical_red_line), null);
            editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    editText.setBackgroundResource(R.drawable.text_cv_et_bg);
                    textView.setTextColor(ContextCompat.getColor(v.getContext(), R.color.colorBlack));
                    textView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.
                            getDrawable(v.getContext(), R.drawable.text_cv_vertical_gray_line), null);
                    if (textView.getText().toString().length() > 2) {
                        editText.setPadding(Util.dip2px(v.getContext(), 72), 0, 0, Util.dip2px(v.getContext(), 8));
                    } else {
                        editText.setPadding(Util.dip2px(v.getContext(), 40), 0, 0, Util.dip2px(v.getContext(), 8));
                    }
                }
            });
        }
    }
}
