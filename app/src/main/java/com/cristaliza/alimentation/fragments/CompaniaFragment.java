package com.cristaliza.alimentation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cristaliza.alimentation.R;
import com.cristaliza.alimentation.MainActivity;
import com.cristaliza.alimentation.PlayVideoActivity;
import com.cristaliza.alimentation.adapters.ProductVideoAdapter;
import com.cristaliza.alimentation.global.ApiManager;
import com.cristaliza.alimentation.global.Constants;
import com.cristaliza.alimentation.models.ItemSerializable;
import com.cristaliza.alimentation.untils.BitmapCreator;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.squareup.picasso.Picasso;

/**
 * Created by samson on 08.07.15.
 */public class CompaniaFragment extends Fragment implements View.OnClickListener {
    private MainActivity mCallingActivity;
    private ImageView ivVolver, ivPhotoComp, ivVideo, ivLogo;
    private TextView tvDescription, tvNameVideo;
    private RelativeLayout rlVideo;
    private EventListener eventListener;
    private Item mCurrentItem, mCompania;
    public static CompaniaFragment newInstance(final ItemSerializable _item) {
        final CompaniaFragment fragment = new CompaniaFragment();
        final Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ITEM_SERIAZ, _item);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallingActivity = (MainActivity) activity;
        if(getArguments() != null){
            mCurrentItem = ((ItemSerializable) getArguments().getSerializable(Constants.ITEM_SERIAZ)).getItem();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compania,null);
        findUI(view);
        setListeners();
        makeDownLoadListener();
        ApiManager.getSecondLevel(eventListener, mCurrentItem);
        return view;
    }
    private void findUI(View _view){
        ivVolver            = (ImageView) _view.findViewById(R.id.ivVolver_FC);
        ivPhotoComp         = (ImageView) _view.findViewById(R.id.ivPhotoComp_FC);
        ivVideo             = (ImageView) _view.findViewById(R.id.ivVideo_FC);
        ivLogo              = (ImageView) _view.findViewById(R.id.ivLogo_FC);
        tvDescription       = (TextView) _view.findViewById(R.id.tvDescription_FC);
        tvNameVideo         = (TextView) _view.findViewById(R.id.tvNameVideo_FC);
        rlVideo             = (RelativeLayout) _view.findViewById(R.id.rlVideo);
        mCallingActivity.setBackground();
        mCallingActivity.setTitle(mCallingActivity.getString(R.string.compania));
        mCallingActivity.setEnableMenu(true);
    }
    private void setListeners(){
        ivVolver.setOnClickListener(this);
        rlVideo.setOnClickListener(this);
    }
    private void makeDownLoadListener(){
        eventListener = new EventListener() {
            @Override
            public void onEvent(Event event) {
                switch (event.getId()){
                    case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                        Toast.makeText(getActivity(), event.getType() + getActivity().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        break;
                    case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED_ID:
                        setData();
                        break;
                }
            }
        };
    }
    private void setData(){
        mCompania = ApiManager.getSecondList().get(0);
        ivPhotoComp.setImageBitmap(BitmapCreator.getBitmap(mCompania.getBackgroundImage()));
        ivLogo.setImageBitmap(BitmapCreator.getBitmap(mCompania.getLogo()));
        Picasso.with(mCallingActivity)
                .load(Constants.URL_YOUTUBE_IMG + ProductVideoAdapter.getYouTubeImageId(mCompania.getExtraVideos().get(0)) + Constants.URL_YOUTUBE_IMG_INDEX)
                .into(ivVideo);
        tvDescription.setText(Html.fromHtml(mCompania.getDescription()));
        tvNameVideo.setText(mCompania.getExtraVideosDescripton().get(0));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivVolver_FC:
                mCallingActivity.onBackPressed();
                break;
            case R.id.rlVideo:
                startVideoActtivity(mCompania.getExtraVideos().get(0));
                break;
        }
    }
    private void startVideoActtivity(String _path) {
        Intent intent = new Intent(mCallingActivity, PlayVideoActivity.class);
        intent.putExtra(Constants.YOUTUBE_VIDEO_ID, _path);
        startActivity(intent);
    }
}