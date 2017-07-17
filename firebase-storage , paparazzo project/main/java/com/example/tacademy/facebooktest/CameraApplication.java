package com.example.tacademy.facebooktest;

import android.app.Application;

import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo;

/**
 * Created by Tacademy on 2017-07-14.
 * here i am!
 * Application을 재정의 할 때 Application을 상속받아 사용.
 * rxpaparazzo는 자체적으로 가용 메모리를 확보해 사용하는 라이브러리이기 때문.
 * 메소드의 개수 65536 개를 초과하여 Dex 처리를 하는 경우
 * 맨 처음 로드시키고 여러 곳에서 전역적으로 사용해야 할 경우
 */

public class CameraApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();

        //파파라조 라이브러리 등록!
        RxPaparazzo.register(this);
    }
}
