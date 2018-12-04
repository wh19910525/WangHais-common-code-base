package com.android.tests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.view.Gravity;

import android.os.Handler;

// A: by wanghai at 2018-11-20 {
import wanghai.util.android.ALog;

import wanghai.util.android.Utils;
import static wanghai.util.android.Utils.DEBUG_FLAG;
import static wanghai.util.android.Utils.LOG_TAG;
// wanghai }

public class TestMainActivity extends Activity {

    final String TAG = LOG_TAG;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        ALog.I(TAG, "onCreate, Start ...");

		setContentView(R.layout.fragment_main);

	}

	public void onResume(){
		super.onResume();
        ALog.I(TAG, "onResume start ...");  

	}

	public void buttonEvent(View view) {

		int ret = 0;

		switch(view.getId()){
			case R.id.button_1:
				ALog.I(TAG, "click button 01.");
				break;

			case R.id.button_2:
				ALog.D(TAG, DEBUG_FLAG, "click button 02.");
				break;
		}
	}

}

