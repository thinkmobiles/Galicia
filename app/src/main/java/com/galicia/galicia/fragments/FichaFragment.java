package com.galicia.galicia.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.galicia.galicia.MainActivity;
import com.galicia.galicia.R;
import com.galicia.galicia.global.Constants;
import com.galicia.galicia.untils.BitmapCreator;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

/**
 * Created by vasia on 19.05.2015.
 */
public class FichaFragment extends Fragment implements View.OnClickListener {

    private MainActivity mCallingActivity;
    private ImageViewTouch ivFicha;
    private ImageView ivClose;
    private String mFichaCata;

    public static FichaFragment newInstance(final String _fichaPath) {
        final FichaFragment fragment = new FichaFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.FICHA_CATA, _fichaPath);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if (getArguments() != null) {
            mFichaCata =  getArguments().getString(Constants.FICHA_CATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ficha, container, false);
        findUI(view);
        setListener();
        makeFichaCata();
        return view;
    }

    private void findUI(final View _view){
        ivFicha = (ImageViewTouch) _view.findViewById(R.id.ivFicha_FF);
        ivClose = (ImageView) _view.findViewById(R.id.ivClose_FF);

        mCallingActivity.setEnableMenu(true);
    }

    private void setListener(){
        ivClose.setOnClickListener(this);
    }

    private void makeFichaCata(){
        ivFicha.setImageBitmap(BitmapCreator.getBitmap(mFichaCata));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivClose_FF:
                mCallingActivity.onBackPressed();
                break;
        }
    }
}
