package com.example.tacademy.fbtest186.util;

import android.util.Log;

/**
 * 싱글톤 객체로서, 이 앱에서 객체가 1개만 생성되는 클레스
 * 용도:유틸리티 용도로 개발자가 생성한 코드
 */
public class U {
    ///////////////////////////////////////////////////////////
    private static U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }
    ///////////////////////////////////////////////////////////

    // 로그 출력
    final String TAG = "T";
    public void log(String msg) {
        // ""+ => null도 죽지않고 null로 표기함
        Log.i(TAG, ""+msg);
    }

}
