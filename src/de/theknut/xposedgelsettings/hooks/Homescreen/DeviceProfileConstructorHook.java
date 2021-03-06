package de.theknut.xposedgelsettings.hooks.homescreen;

import android.content.Context;

import de.theknut.xposedgelsettings.hooks.Common;
import de.theknut.xposedgelsettings.hooks.HooksBaseClass;
import de.theknut.xposedgelsettings.hooks.ObfuscationHelper.Fields;
import de.theknut.xposedgelsettings.hooks.PreferencesHelper;

import static de.robv.android.xposed.XposedHelpers.setIntField;

public final class DeviceProfileConstructorHook extends HooksBaseClass {

    // http://androidxref.com/4.4.2_r1/xref/packages/apps/Launcher3/src/com/android/launcher3/DynamicGrid.java#99
    // DeviceProfile(String n, float w, float h, float r, float c, float is, float its, float hs, float his)

    public static int NAME = 0;
    public static int MINWIDTHDPS = 1;
    public static int MINHEIGHTDPS = 2;
    public static int NUMROWS = 3;
    public static int NUMCOLUMNS = 4;
    public static int ICONSIZE = 5;
    public static int ICONTEXTSIZE = 6;
    public static int NUMHOTSEATICONS = 7;
    public static int HOTSEATICONSIZE = 8;

    @Override
    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

        // making sure to only hook to the appropriate constructor
        if (!(param.args[NAME] instanceof Context)) {

            if (PreferencesHelper.changeGridSizeHome) {
                if (PreferencesHelper.yCountHomescreenVertical != -1) {
                    // set custom row count
                    param.args[NUMROWS] = PreferencesHelper.yCountHomescreenVertical;
                }

                if (PreferencesHelper.xCountHomescreenVertical != -1) {
                    // set custom column count
                    param.args[NUMCOLUMNS] = PreferencesHelper.xCountHomescreenVertical;
                }
            }

            if (PreferencesHelper.iconSettingsSwitchHome) {

                // calculating custom sizes
                float newIconSize = (float) (Math.ceil((Float) param.args[ICONSIZE] * (PreferencesHelper.iconSize / 100.0)));
                float newIconTextSize = (float) (Math.ceil((Float) param.args[ICONTEXTSIZE] * (PreferencesHelper.iconTextSize / 100.0)));

                // some validation
                if (newIconSize > 0.0 ) {
                    param.args[ICONSIZE] = Common.NEW_ICON_SIZE = newIconSize;
                }
                else {
                    log("Didn't change icon size! Value was " + newIconSize);
                }

                // some validation
                if (newIconTextSize > 0.0) {
                    param.args[ICONTEXTSIZE] = newIconTextSize;
                }
                else {
                    log("Didn't change icon text size! Value was " + newIconTextSize);
                }
            }

            if (PreferencesHelper.appdockSettingsSwitch) {
                float newHotseatIconSize = (float) (Math.ceil((Float) param.args[HOTSEATICONSIZE] * (PreferencesHelper.appdockIconSize / 100.0)));

                // some validation
                if (newHotseatIconSize > 0.0) {
                    param.args[HOTSEATICONSIZE] = Common.NEW_HOTSEAT_ICON_SIZE = newHotseatIconSize;
                }
                else {
                    log("Didn't change hotseat icon size! Value was " + newHotseatIconSize);
                }

                int hotseatBarHeight = Math.round((Float)param.args[ICONSIZE]) + 24;
                setIntField(param.thisObject, Fields.dpHotseatBarHeightPx, hotseatBarHeight);
            }
        }
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param) throws Throwable {

        if (PreferencesHelper.noAllAppsButton) {
            setIntField(param.thisObject, Fields.hotseatAllAppsRank, 50);
        } else {
            if (PreferencesHelper.homescreenAllAppsPosition != -1) {
                setIntField(param.thisObject, Fields.hotseatAllAppsRank, PreferencesHelper.homescreenAllAppsPosition - 1);
            }
        }

        if (PreferencesHelper.appDockCount != -1) {
            setIntField(param.thisObject, Fields.dpNumHotseatIcons, PreferencesHelper.appDockCount);
        }
    }
}