package com.aren.backward;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class SplashActivity extends Activity {

    private static String TAG = "SPLASH_ACTIVITY";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler() , 2000); // 3초 후에 hd Handler 실행
    }

//
//    public boolean isOnline() {
//        ConnectivityManager cm =
//                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        return netInfo != null && netInfo.isConnectedOrConnecting();
//    }

    private class splashhandler implements Runnable{
        public void run() {
//            if (!isOnline()) {
//                SplashActivity.this.finish();
//                System.exit(0);
//            } else {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent); // 로딩이 끝난후 이동할 Activity
            SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
//            }
        }
    }
}
