package com.mthwate.dominion.client;

import android.content.pm.ActivityInfo;
import com.jme3.app.AndroidHarness;
import com.jme3.system.android.AndroidConfigChooser;

public class MainActivity extends AndroidHarness {

	public MainActivity() {
		appClass = "com.mthwate.dominion.client.ClientApp";
		eglConfigType = AndroidConfigChooser.ConfigType.BEST;
		exitDialogTitle = "Exit";
		exitDialogMessage = "Are you sure you want to exit?";
		screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
		mouseEventsEnabled = true;
	}

}