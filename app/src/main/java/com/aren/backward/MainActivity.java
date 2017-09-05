package com.aren.backward;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MAINACTIVITY";
    private EditText inputText;
    private TextView resultText;
    private Button copyBtn, shareBtn;
    private String reversedText;
    private String beforeText;
    private AdView mAdView;
    private int countForAds = 0;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 전면 광고
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // 배너광고
        MobileAds.initialize(this, "ca-app-pub-2778546304304506~2656046916");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        inputText = (EditText) findViewById(R.id.inputText);
        resultText = (TextView) findViewById(R.id.resultText);
        copyBtn = (Button) findViewById(R.id.copyBtn);
        shareBtn = (Button) findViewById(R.id.shareBtn);

        inputText.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {

            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforeText = s.toString();
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("text  : ", s.toString());
                if (inputText.getLineCount() >= 10)
                {
                    inputText.setText(beforeText);
                    inputText.setSelection(inputText.length());
                }
                StringBuffer strBuffer = new StringBuffer();
                strBuffer.append(s.toString());
                reversedText = strBuffer.reverse().toString();
                resultText.setText(reversedText);
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "click copy button");
                Toast.makeText(getApplicationContext(), "Copy to clipboard.", Toast.LENGTH_LONG).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(reversedText, reversedText + " by backward");
                clipboard.setPrimaryClip(clip);

                countForAds++;
                showAds();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, reversedText + " by backward");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                countForAds++;
                showAds();
            }
        });
    }

    void showAds() {
        if (mInterstitialAd.isLoaded() && (countForAds%3 == 0)) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }
}
