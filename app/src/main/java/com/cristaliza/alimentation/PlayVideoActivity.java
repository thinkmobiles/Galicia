package com.cristaliza.alimentation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cristaliza.alimentation.adapters.ProductVideoAdapter;
import com.cristaliza.alimentation.global.Constants;

/**
 * Created by Feltsan on 04.06.2015.
 */
public class PlayVideoActivity extends Activity {
    WebView videoContainer;
    String videoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        findView();

        Intent intent = getIntent();
        videoId = (String)intent.getExtras().get(Constants.YOUTUBE_VIDEO_ID);

        if(!videoId.isEmpty()){
            playVideo(videoId);
//            this.finish();
        }
    }

    public void findView()    {
        videoContainer = (WebView) findViewById(R.id.wv_video_container);
    }

    public void playVideo(String _videoId){
        videoContainer.loadUrl(Constants.URL_YOUTUBE_EMBED +
                ProductVideoAdapter.getYouTubeImageId(_videoId) +
                Constants.URL_YOUTUBE_AUTOPLAY);

//        videoContainer.setWebViewClient(new WebViewClient());
        videoContainer.getSettings().setJavaScriptEnabled(true);
        // web.getSettings().setDomStorageEnabled(true);

        videoContainer.getSettings().setAllowContentAccess(true);
        WebSettings webSettings = videoContainer.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        videoContainer.canGoBack();


       videoContainer.setWebChromeClient(new WebChromeClient() {
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoContainer.destroy();
        this.finish();
    }


}
