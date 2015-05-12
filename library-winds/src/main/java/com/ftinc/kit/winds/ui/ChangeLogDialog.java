package com.ftinc.kit.winds.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.XmlRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ftinc.kit.font.Face;
import com.ftinc.kit.font.FontLoader;
import com.ftinc.kit.winds.R;
import com.ftinc.kit.winds.Winds;

import butterknife.ButterKnife;

/**
 * Project: android-52Kit
 * Package: com.ftinc.kit.winds.ui
 * Created by drew.heavner on 3/16/15.
 */
public class ChangeLogDialog extends DialogFragment implements View.OnClickListener {

    public static ChangeLogDialog createInstance(@XmlRes int configResId){
        ChangeLogDialog dialog = new ChangeLogDialog();
        Bundle args = new Bundle();
        args.putInt(EXTRA_CONFIG, configResId);
        dialog.setArguments(args);
        return dialog;
    }

    /***********************************************************************************************
     *
     * Variables
     *
     */

    public static final String EXTRA_CONFIG = "extra_config_id";
    public int mConfigResId;

    private FrameLayout mContainer;
    private TextView mOkButton;
    private RecyclerView mRecycler;

    /***********************************************************************************************
     *
     * Lifecycle Methods
     *
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mConfigResId = getArguments().getInt(EXTRA_CONFIG);

        //setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Theme_AppCompat_Light);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_changelog, container, false);

        mContainer = ButterKnife.findById(view, R.id.container);
        mOkButton = ButterKnife.findById(view, R.id.button_ok);
        FontLoader.apply(mOkButton, Face.ROBOTO_MEDIUM);

        // Generate Winds Recycler View
        mRecycler = Winds.draft(getActivity(), mConfigResId);
        mContainer.addView(mRecycler);

        mOkButton.setOnClickListener(this);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    /***********************************************************************************************
     *
     * Helper Methods
     *
     */

    @Override
    public void onClick(View v) {
        dismiss();
    }


}
