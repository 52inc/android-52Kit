package com.ftinc.kit.eula;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import rx.android.view.OnClickEvent;
import rx.android.view.ViewObservable;
import rx.functions.Action1;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.eula
 * Created by drew.heavner on 6/16/15.
 */
public class EulaActivity extends AppCompatActivity {

    /***********************************************************************************************
     *
     * Static Methods
     *
     */

    /**
     * Generate a pre-populated intent to launch this activity with
     *
     * @param ctx           the context to create the intent with
     * @param logoResId     the resource id of the logo you want to use
     * @param eulaText      the EULA text to display
     * @return              the intent to launch
     */
    public static Intent createIntent(Context ctx, int logoResId, CharSequence eulaText){
        Intent intent = new Intent(ctx, EulaActivity.class);
        intent.putExtra(EXTRA_LOGO, logoResId);
        intent.putExtra(EXTRA_EULA_TEXT, eulaText);
        return intent;
    }

    /***********************************************************************************************
     *
     * Constants
     *
     */

    public static final String EXTRA_LOGO = "extra_logo_resource_id";
    public static final String EXTRA_EULA_TEXT = "extra_eula_text";

    /***********************************************************************************************
     *
     * Variables
     *
     */

    private ImageView mLogo;
    private TextView mEulaText;
    private TextView mActionCancel;
    private TextView mActionAccept;

    private int mLogoResId;
    private CharSequence mEulaContent;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);
        ButterKnife.inject(this);
        initUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_LOGO, mLogoResId);
        outState.putCharSequence(EXTRA_EULA_TEXT, mEulaContent);
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    void initUI(){
        mLogo = ButterKnife.findById(this, R.id.logo);
        mEulaText = ButterKnife.findById(this, R.id.eula_text);
        mActionCancel = ButterKnife.findById(this, R.id.action_cancel);
        mActionAccept = ButterKnife.findById(this, R.id.action_accept);

        ViewObservable.clicks(mActionCancel)
                .subscribe(new Action1<OnClickEvent>() {
                    @Override
                    public void call(OnClickEvent onClickEvent) {

                    }
                });

        ViewObservable.clicks(mActionAccept)
                .subscribe(new Action1<OnClickEvent>() {
                    @Override
                    public void call(OnClickEvent onClickEvent) {

                    }
                });
    }

    void parseExtras(Bundle icicle){
        Intent intent = getIntent();

        if(intent != null){
            mLogoResId = intent.getIntExtra(EXTRA_LOGO, -1);
            if(mLogoResId != -1) mLogo.setImageResource(mLogoResId);

            mEulaContent = intent.getCharSequenceExtra(EXTRA_EULA_TEXT);
            mEulaText.setText(mEulaContent);
        }

        if(intent != null){
            mLogoResId = icicle.getInt(EXTRA_LOGO, -1);
            if(mLogoResId != -1) mLogo.setImageResource(mLogoResId);

            mEulaContent = icicle.getCharSequence(EXTRA_EULA_TEXT, "No Text Found");
            mEulaText.setText(mEulaContent);
        }

    }

}
