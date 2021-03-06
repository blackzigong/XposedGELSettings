package de.theknut.xposedgelsettings.hooks.androidintegration;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.theknut.xposedgelsettings.R;
import de.theknut.xposedgelsettings.hooks.Common;
import de.theknut.xposedgelsettings.hooks.HooksBaseClass;
import de.theknut.xposedgelsettings.hooks.Utils;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getObjectField;

/**
 * Created by Alexander Schulz on 29.07.2014.
 */
public class AppInfo extends HooksBaseClass {

    static Context SettingsContext;
    static Context XGELSContext;
    static LayoutInflater inflater;
    static Set<String> hiddenApps;
    static final int USER_CURRENT = -2;

    public static void initAllHooks(LoadPackageParam lpparam) {

        final String key = "hiddenapps";


        findAndHookMethod("com.android.settings.applications.InstalledAppDetails", lpparam.classLoader, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodHook() {

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (DEBUG) log(param, "AndroidSettings: Add \"Hide app\" checkbox");

                if (inflater == null) {
                    SettingsContext = (Context) callMethod(param.thisObject, "getActivity");
                    XGELSContext = SettingsContext.createPackageContext(Common.PACKAGE_NAME, Context.CONTEXT_IGNORE_SECURITY);
                    inflater = (LayoutInflater) XGELSContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }

                final SharedPreferences prefs = XGELSContext.getSharedPreferences(Common.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
                hiddenApps = prefs.getStringSet(key, new HashSet<String>());

                Object mAppEntry = getObjectField(param.thisObject, "mAppEntry");
                ApplicationInfo info = (ApplicationInfo) getObjectField(mAppEntry, "info");

                View mNotificationSwitch = (View) getObjectField(param.thisObject, "mNotificationSwitch");
                ViewGroup parent = (ViewGroup) mNotificationSwitch.getParent();

                RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.androidsettingscheckbox, null, false);
                CheckBox checkbox = (CheckBox) view.findViewById(R.id.hide_app_switch);
                checkbox.setTag(SettingsContext.getPackageManager().getLaunchIntentForPackage(info.packageName).getComponent().flattenToString());
                checkbox.setChecked(hiddenApps.contains(checkbox.getTag()));
                checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        hiddenApps = prefs.getStringSet(key, new HashSet<String>());
                        if (isChecked) {
                            if (!hiddenApps.contains(buttonView.getTag())) {
                                // app is not in the list, so lets add it
                                hiddenApps.add((String)buttonView.getTag());
                            }
                        }
                        else {
                            if (hiddenApps.contains(buttonView.getTag())) {
                                // app is in the list but the checkbox is no longer checked, we can remove it
                                hiddenApps.remove(buttonView.getTag());
                            }
                        }

                        Utils.saveToSettings(SettingsContext, key, hiddenApps, true);
                    }
                });

                parent.addView(view);
            }
        });
    }
}