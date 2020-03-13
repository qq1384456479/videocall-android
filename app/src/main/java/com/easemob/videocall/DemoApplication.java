/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easemob.videocall;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.support.multidex.MultiDex;

import com.easemob.videocall.utils.ConferenceSession;
import com.hyphenate.util.EMLog;
import com.easemob.videocall.utils.ConferenceInfo;


public class DemoApplication extends Application implements Thread.UncaughtExceptionHandler {

	public static Context applicationContext;
	private static DemoApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";

	static public ConferenceInfo conferenceInstance;

	private ConferenceSession  conferenceSession;

	/**
	 * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
	 */
	public static String currentUserNick = "";

	@Override
	public void onCreate() {
		MultiDex.install(this);
		super.onCreate();
        applicationContext = this;
        instance = this;

		//init demo helper
        DemoHelper.getInstance().init(applicationContext);

		conferenceInstance = ConferenceInfo.getInstance();

		addErrorListener();
	}

	private void addErrorListener() {
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public static DemoApplication getInstance() {
		return instance;
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		e.printStackTrace();
		EMLog.e("uncaughtException : ", e.getMessage());
		System.exit(1);
		Process.killProcess(Process.myPid());
	}
}
