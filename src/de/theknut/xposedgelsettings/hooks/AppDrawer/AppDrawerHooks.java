package de.theknut.xposedgelsettings.hooks.appdrawer;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.theknut.xposedgelsettings.hooks.Common;
import de.theknut.xposedgelsettings.hooks.HooksBaseClass;
import de.theknut.xposedgelsettings.hooks.ObfuscationHelper.Classes;
import de.theknut.xposedgelsettings.hooks.ObfuscationHelper.Fields;
import de.theknut.xposedgelsettings.hooks.ObfuscationHelper.Methods;
import de.theknut.xposedgelsettings.hooks.PreferencesHelper;
import de.theknut.xposedgelsettings.hooks.appdrawer.tabsandfolders.AddTabsAndFolders;
import de.theknut.xposedgelsettings.hooks.appdrawer.tabsandfolders.TabHelper;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.getIntField;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.setIntField;

public class AppDrawerHooks extends HooksBaseClass {

    public static void initAllHooks(LoadPackageParam lpparam) {

        // save an instance of the app drawer object
        XposedBridge.hookAllConstructors(Classes.AppsCustomizePagedView, new AppsCustomizePagedViewConstructorHook());

        if (PreferencesHelper.iconSettingsSwitchApps) {
            // changing the appearence of the icons in the app drawer
            XposedBridge.hookAllMethods(Classes.PagedViewIcon, Methods.pviApplyFromApplicationInfo, new ApplyFromApplicationInfoHook());
        }

        // modify app drawer grid
        if (Common.PACKAGE_OBFUSCATED) {
            findAndHookMethod(Classes.DeviceProfile, Methods.dpUpdateFromConfiguration, float.class, Integer.TYPE, Resources.class, DisplayMetrics.class, new UpdateFromConfigurationHook());
        } else {
            XposedBridge.hookAllMethods(Classes.DeviceProfile, Methods.dpUpdateFromConfiguration, new UpdateFromConfigurationHook());
        }

        if (Common.IS_TREBUCHET) {
            // set the background pref_color of the app drawer
            XposedBridge.hookAllConstructors(Classes.AppsCustomizeLayout, new AppsCustomizeLayoutConstructorHook());
        }
        else {
            // set the background pref_color of the app drawer
            if (Common.PACKAGE_OBFUSCATED) {
                findAndHookMethod(Classes.AppsCustomizeTabHost, Methods.acthOnTabChanged, Classes.AppsCustomizeContentType, new OnTabChangedHook());
            } else {
                findAndHookMethod(Classes.AppsCustomizeTabHost, Methods.acthOnTabChanged, String.class, new OnTabChangedHook());
            }
        }

        if (PreferencesHelper.continuousScroll) {
            // open app drawer on overscroll of last page
            findAndHookMethod(Classes.AppsCustomizePagedView, Methods.acpvOverScroll, float.class, new OverScrollAppDrawerHook());
        }

        if (PreferencesHelper.closeAppdrawerAfterAppStarted) {
            findAndHookMethod(Classes.AppsCustomizePagedView, "onClick", View.class, new OnClickHook());
        }

        findAndHookMethod(Classes.Workspace, Methods.wOnLauncherTransitionEnd, Classes.Launcher, boolean.class, boolean.class, new XC_MethodHook() {

            int TOWORKSPACE = 2;

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {


                if ((Boolean) param.args[TOWORKSPACE]) {
                    if (PreferencesHelper.appdrawerRememberLastPosition) {
                        if (!Common.OVERSCROLLED) {
                            Object acpv = getObjectField(Common.LAUNCHER_INSTANCE, Fields.lAppsCustomizePagedView);
                            Common.APPDRAWER_LAST_PAGE_POSITION = getIntField(acpv, Fields.acpvCurrentPage);
                        }

                        if (!Common.OVERSCROLLED && !Common.IS_TREBUCHET && !TabHelper.getInstance().getCurrentTabData().isWidgetsTab()) {
                            Common.APPDRAWER_LAST_TAB_POSITION = TabHelper.getInstance().getTabHost().getCurrentTab();
                        }

                        if (DEBUG)
                            log(param, "AppDrawerHooks: get current position - " + Common.APPDRAWER_LAST_PAGE_POSITION);
                    }

                    Common.OVERSCROLLED = false;
                } else {
                    Common.ORIENTATION = Common.LAUNCHER_CONTEXT.getResources().getConfiguration().orientation;
                }
            }
        });

        findAndHookMethod(Classes.Workspace, Methods.wOnTransitionPrepare, new XC_MethodHook() {

            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                if (Common.OVERSCROLLED) return;

                if (PreferencesHelper.appdrawerRememberLastPosition) {
                    if (!Common.IS_TREBUCHET && !TabHelper.getInstance().getCurrentTabData().isWidgetsTab()) {
                        int lastTab = TabHelper.getInstance().getTabHost().getTabWidget().getTabCount() - 1;
                        if (Common.APPDRAWER_LAST_TAB_POSITION > lastTab) {
                            Common.APPDRAWER_LAST_TAB_POSITION = lastTab;
                        }

                        TabHelper.getInstance().setCurrentTab(Common.APPDRAWER_LAST_TAB_POSITION);
                    }

                    Object acpv = getObjectField(Common.LAUNCHER_INSTANCE, Fields.lAppsCustomizePagedView);
                    int lastPage = (Integer) callMethod(acpv, "getChildCount") - 1;

                    if (Common.APPDRAWER_LAST_PAGE_POSITION > lastPage) {
                        Common.APPDRAWER_LAST_PAGE_POSITION = lastPage;
                    }

                    if (DEBUG)
                        log(param, "AppDrawerHooks: set to last position " + Common.APPDRAWER_LAST_PAGE_POSITION);
                    callMethod(acpv, Methods.acpvSetCurrentPage, Common.APPDRAWER_LAST_PAGE_POSITION);
                }
            }
        });

        if (PreferencesHelper.changeGridSizeApps) {
            XposedBridge.hookAllMethods(Classes.AppsCustomizePagedView, Methods.acpvUpdatePageCounts, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    if (Common.LAUNCHER_CONTEXT.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        setIntField(param.thisObject, Fields.acpvCellCountY, Common.ALL_APPS_Y_COUNT_HORIZONTAL);
                        setIntField(param.thisObject, Fields.acpvCellCountX, Common.ALL_APPS_X_COUNT_HORIZONTAL);
                    } else {
                        setIntField(param.thisObject, Fields.acpvCellCountY, Common.ALL_APPS_Y_COUNT_VERTICAL);
                        setIntField(param.thisObject, Fields.acpvCellCountX, Common.ALL_APPS_X_COUNT_VERTICAL);
                    }
                }
            });
        }

        if (Common.PACKAGE_OBFUSCATED) {
            findAndHookMethod(Classes.AppsCustomizePagedView, Methods.acpvSetAllAppsPadding, Rect.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Rect r = (Rect) param.args[0];
                    r.left = 0;
                    r.right = 0;
                }
            });
        } else {
            findAndHookMethod(Classes.AppsCustomizePagedView, "setupPage", Classes.AppsCustomizeCellLayout, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ViewGroup vg = (ViewGroup) param.args[0];
                    vg.setPadding(0, vg.getPaddingTop(), 0, vg.getPaddingBottom());
                }
            });
        }

        // hiding apps from the app drawer
        findAndHookMethod(Classes.AppsCustomizePagedView, Methods.acpvSetApps, ArrayList.class, new AllAppsListAddHook());
        findAndHookMethod(Classes.AppsCustomizePagedView, Methods.acpvUpdateApps, ArrayList.class, new AllAppsListAddHook());
        findAndHookMethod(Classes.AppsCustomizePagedView, Methods.acpvRemoveApps, ArrayList.class, new AllAppsListAddHook());

        AddTabsAndFolders.initAllHooks(lpparam);
    }
}