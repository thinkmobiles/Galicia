package com.cristaliza.nacional.custom;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.cristaliza.nacional.R;
import com.cristaliza.nacional.global.Constants;
import com.cristaliza.nacional.global.FragmentReplacer;

import java.io.Serializable;

/**
 * Created by vasia on 27.05.2015.
 */
public class CustomDialog extends Fragment implements View.OnClickListener {

    private Button btnPositive, btnNegative;
    private TextView tvMessage;
    private DialogParams mParams;
    private FrameLayout flEmptyField1, flEmptyField2, flBackground;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getArguments() != null){
            mParams = (DialogParams) getArguments().getSerializable(Constants.PARAMS);
            getArguments().remove(Constants.PARAMS);
        }
    }

    public void show(FragmentActivity _activity){
        FragmentReplacer.addFragment(_activity, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View inflaterView = inflater.inflate(R.layout.dialod_custom, container, false);
        findViews(inflaterView);
        setListeners();
        createVisibleViews();
        return inflaterView;
    }

    private void findViews(final View _view){
        btnPositive    = (Button) _view.findViewById(R.id.btnPositive_DC);
        btnNegative    = (Button) _view.findViewById(R.id.btnNegative_DC);
        tvMessage      = (TextView) _view.findViewById(R.id.tvMessage_DC);
        flEmptyField1  = (FrameLayout) _view.findViewById(R.id.flEmptyField1_DC);
        flEmptyField2  = (FrameLayout) _view.findViewById(R.id.flEmptyField2_DC);
        flBackground   = (FrameLayout) _view.findViewById(R.id.flBackground_DC);
    }

    private void setListeners(){
        btnPositive.setOnClickListener(this);
        btnNegative.setOnClickListener(this);
        flEmptyField1.setOnClickListener(this);
        flEmptyField2.setOnClickListener(this);
        flBackground.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPositive_DC:
                if (mParams.onPositiveClickListener != null){
                    mParams.onPositiveClickListener.onClick(btnPositive);
                }
                break;
            case R.id.btnNegative_DC:
                if (mParams.onNegativeClickListener != null){
                    mParams.onNegativeClickListener.onClick(btnNegative);
                }
                break;
        }
        FragmentReplacer.popSupBackStack(getActivity());
    }

    private void createVisibleViews(){

        if (mParams.positiveTxt != null){
            btnPositive.setVisibility(View.VISIBLE);
            btnPositive.setText(mParams.positiveTxt);
        }

        if (mParams.negativeTxt != null){
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(mParams.negativeTxt);
        }

        if (mParams.message != null){
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(mParams.message);
        }

    }

    public static class Builder {

        private DialogParams params;

        public Builder(){
            params = new DialogParams();
        }

        public CustomDialog createDialog(){
            final CustomDialog fragment = new CustomDialog();
            final Bundle bundle = new Bundle();
            bundle.putSerializable(Constants.PARAMS, params);
            fragment.setArguments(bundle);
            return fragment;
        }


        public Builder setPositiveButton(String _txt, View.OnClickListener _listener){
            params.positiveTxt = _txt;
            params.onPositiveClickListener = _listener;
            return this;
        }

        public Builder setNegativeButton(String _txt, View.OnClickListener _listener){
            params.negativeTxt = _txt;
            params.onNegativeClickListener = _listener;
            return this;
        }

        public Builder setMessage(String _message){
            params.message = _message;
            return this;
        }
    }

    public static class DialogParams implements Serializable {
        private String positiveTxt, negativeTxt, message;
        private View.OnClickListener onPositiveClickListener, onNegativeClickListener;
    }
}
