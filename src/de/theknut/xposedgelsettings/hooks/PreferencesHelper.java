package de.theknut.xposedgelsettings.hooks;

import android.graphics.Color;

import java.util.HashSet;
import java.util.Set;

import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.theknut.xposedgelsettings.hooks.appdrawer.tabsandfolders.Tab;

import static de.robv.android.xposed.XposedHelpers.callMethod;

public class PreferencesHelper {
    public static XSharedPreferences prefs = new XSharedPreferences(Common.PACKAGE_NAME);
    public static boolean Debug = prefs.getBoolean("debug", false);
    public static boolean hideSearchBar;
    public static boolean searchBarOnDefaultHomescreen;
    public static boolean autoHideSearchBar;
    public static boolean hideIconLabelHome;
    public static boolean hideIconLabelApps;
    public static boolean changeGridSizeHome;
    public static boolean changeGridSizeApps;
    public static boolean iconSettingsSwitchHome;
    public static boolean iconSettingsSwitchApps;
    public static boolean appdockSettingsSwitch;
    public static boolean hidePageIndicator;
    public static boolean enableRotation;
    public static boolean resizeAllWidgets;
    public static boolean homescreenIconLabelShadow;
    public static boolean appdrawerIconLabelShadow;
    public static boolean longpressAllAppsButton;
    public static boolean disableWallpaperScroll;
    public static boolean hideAppDock;
    public static boolean autoHideAppDock;
    public static boolean lockHomescreen;
    public static boolean continuousScroll;
    public static boolean continuousScrollWithAppDrawer;
    public static boolean closeAppdrawerAfterAppStarted;
    public static boolean noAllAppsButton;

    public static boolean hideClock;
    public static boolean dynamicHomebutton;
    public static boolean dynamicBackbutton;
    public static boolean dynamicRecentsbutton;
    public static boolean dynamicIconBackbutton;
    public static boolean dynamicIconHomebutton;
    public static boolean dynamicIconRecentsbutton;
    public static boolean dynamicAnimateIconHomebutton;
    public static boolean dynamicAnimateIconBackbutton;
    public static boolean dynamicAnimateIconRecentsbutton;
    public static boolean dynamicBackButtonOnEveryScreen;

    public static boolean overlappingWidgets;
    public static boolean homescreenFolderSwitch;
    public static boolean homescreenFolderNoLabel;
    public static boolean gesture_appdrawer;
    public static boolean appdrawerRememberLastPosition;
    public static int xCountHomescreenVertical;
    public static int yCountHomescreenVertical;
    public static int xCountHomescreenHorizontal;
    public static int yCountHomescreenHorizontal;
    public static int xCountAllAppsVertical;
    public static int yCountAllAppsVertical;
    public static int xCountAllAppsHorizontal;
    public static int yCountAllAppsHorizontal;
    public static int appDockCount;
    public static int iconSize;
    public static int appdockIconSize;
    public static int iconTextSize;
    public static int homescreenIconLabelColor;
    public static int appdrawerIconLabelColor;
    public static int appdrawerBackgroundColor;
    public static int appdrawerFolderStyleBackgroundColor;
    public static int appDockBackgroundColor;
    public static int homescreenFolderColor;
    public static int homescreenFolderAppTextColor;
    public static int homescreenFolderNameTextColor;
    public static int homescreenFolderPreviewColor;
    public static int defaultHomescreen;
    public static int workspaceRect;
    public static int appdockRect;
    public static int homescreenAllAppsPosition;
    public static int smartFolderMode;
    public static int searchbarStyle;
    public static int contextmenuMode;
    public static int pageIndicatorMode;
    public static double scrolldevider;

    public static String gesture_one_up_left;
    public static String gesture_one_up_middle;
    public static String gesture_one_up_right;
    public static String gesture_one_down_left;
    public static String gesture_one_down_middle;
    public static String gesture_one_down_right;
    public static String gesture_double_tap;
    public static boolean gesture_double_tap_only_on_wallpaper;

    public static Set<String> selectedIcons;
    public static Set<String> shortcutIcons;
    public static Set<String> folderIcons;
    public static Set<String> hiddenApps;
    public static Set<String> hiddenWidgets;
    public static Set<String> layerPositions;
    public static Set<String> appdrawerTabData;
    public static Set<String> appdrawerFolderData;
    public static Set<String> iconGestures;
    public static Set<String> appNames;

    public static String iconpack;
    public static boolean iconPackAutoApply;
    public static boolean iconPackHide;

    public static String notificationDialerApp;
    public static String notificationSMSApp;
    public static boolean enableBadges;
    public static boolean hideBadgesFromAppDrawer;
    public static int notificationBadgeFrameSize;
    public static int notificationBadgeTextSize;
    public static int notificationBadgeCornerRadius;
    public static int notificationBadgeLeftRightPadding;
    public static int notificationBadgeTopBottomPadding;
    public static int notificationBadgeBackgroundColor;
    public static int notificationBadgeTextColor;
    public static int notificationBadgeFrameColor;
    public static boolean notificationBadgeKeepSize;

    public static boolean enableLLauncher;
    public static boolean hideWorkspaceShadow;
    public static boolean unlimitedFolderSize;
    public static boolean alwaysShowSayOKGoogle;
    public static int glowColor;
    public static int searchbarPrimaryColor;
    public static boolean transparentSystemBars;
    public static boolean quicksettingsLockDesktop;
    public static int notificationBadgePosition;
    public static boolean enableAppDrawerTabs;
    public static boolean overrideSettingsButton;
    public static int pageIndicatorColor;
    public static boolean moveTabHostBottom;
    public static boolean appdockShowLabels;
    public static int iconSizeAppDrawer;
    public static boolean appdrawerSwipeTabs;
    public static boolean searchBarWeatherWidget;
    public static boolean excludeAppsUsedInTabs;
    public static boolean autoHideHomeIcons;

    public static void init() {
        long time = System.currentTimeMillis();
        prefs = new XSharedPreferences(Common.PACKAGE_NAME);
        prefs.reload();

        Debug = prefs.getBoolean("debug", false);
        hideSearchBar = prefs.getBoolean("hidesearchbar", false);
        searchBarOnDefaultHomescreen = prefs.getBoolean("searchbarondefaulthomescreen", false);
        autoHideSearchBar = prefs.getBoolean("autohidehidesearchbar", false);
        searchBarWeatherWidget = prefs.getBoolean("searchbarweatherwidget", false);
        hideIconLabelHome = prefs.getBoolean("hideiconhomescreen", false);
        hideIconLabelApps = prefs.getBoolean("hideiconappdrawer", false);
        changeGridSizeHome = prefs.getBoolean("changegridsizehome", false);
        changeGridSizeApps = prefs.getBoolean("changegridsizeapps", false);
        iconSettingsSwitchHome = prefs.getBoolean("iconsettingsswitchhome", false);
        iconSettingsSwitchApps = prefs.getBoolean("iconsettingsswitchapps", false);
        appdockSettingsSwitch = prefs.getBoolean("appdocksettingsswitch", false);
        appdockShowLabels = prefs.getBoolean("appdockshowlabels", false);
        hidePageIndicator = prefs.getBoolean("hidepageindicator", false);
        enableRotation = prefs.getBoolean("enablerotation", false);
        resizeAllWidgets = prefs.getBoolean("resizeallwidgets", false);
        homescreenIconLabelShadow = prefs.getBoolean("homescreeniconlabelshadow", true);
        appdrawerIconLabelShadow = prefs.getBoolean("appdrawericonlabelshadow", true);
        longpressAllAppsButton = prefs.getBoolean("longpressallappsbutton", false);
        disableWallpaperScroll = prefs.getBoolean("disablewallpaperscroll", false);
        hideAppDock = prefs.getBoolean("hide_appdock", false);
        autoHideAppDock = prefs.getBoolean("autohideappdock", false);
        lockHomescreen = prefs.getBoolean("lockhomescreen", false);
        continuousScroll = prefs.getBoolean("continuousscroll", false);
        continuousScrollWithAppDrawer = prefs.getBoolean("continuousscrollwithappdrawer", false);
        closeAppdrawerAfterAppStarted = prefs.getBoolean("closeappdrawerafterappstarted", false);
        noAllAppsButton = prefs.getBoolean("noallappsbutton", false);

        hideClock = prefs.getBoolean("hideclock", false);
        dynamicHomebutton = prefs.getBoolean("dynamichomebutton", false);
        dynamicBackbutton = prefs.getBoolean("dynamicbackbutton", false);
        dynamicRecentsbutton = prefs.getBoolean("dynamicrecentsbutton", false);
        dynamicIconBackbutton = prefs.getBoolean("changeicondynamicbackbutton", false);
        dynamicIconHomebutton = prefs.getBoolean("changeicondynamichomebutton", false);
        dynamicIconRecentsbutton = prefs.getBoolean("changeicondynamicrecentsbutton", false);
        dynamicAnimateIconHomebutton = prefs.getBoolean("animatedynamichomebutton", false);
        dynamicAnimateIconBackbutton = prefs.getBoolean("animatedynamicbackbutton", false);
        dynamicAnimateIconRecentsbutton = prefs.getBoolean("dynamicanimateiconrecentsbutton", false);
        dynamicBackButtonOnEveryScreen = prefs.getBoolean("dynamicbackbuttononeveryscreen", false);

        overlappingWidgets = prefs.getBoolean("overlappingwidgets", false);
        homescreenFolderSwitch = prefs.getBoolean("homescreenfolderswitch", false);
        homescreenFolderNoLabel = prefs.getBoolean("homescreenfoldernolabel", false);
        gesture_appdrawer = prefs.getBoolean("gesture_appdrawer", false);
        appdrawerRememberLastPosition = prefs.getBoolean("appdrawerrememberlastposition", false);
        xCountHomescreenVertical = Integer.parseInt(prefs.getString("xcounthomescreen", "-1"));
        yCountHomescreenVertical = Integer.parseInt(prefs.getString("ycounthomescreen", "-1"));
        xCountHomescreenHorizontal = Integer.parseInt(prefs.getString("xcounthomescreenhorizontal", "-1"));
        yCountHomescreenHorizontal = Integer.parseInt(prefs.getString("ycounthomescreenhorizontal", "-1"));
        xCountAllAppsVertical = Integer.parseInt(prefs.getString("xcountallapps", "-1"));
        yCountAllAppsVertical = Integer.parseInt(prefs.getString("ycountallapps", "-1"));
        xCountAllAppsHorizontal = Integer.parseInt(prefs.getString("xcountallappshorizontal", "-1"));
        yCountAllAppsHorizontal = Integer.parseInt(prefs.getString("ycountallappshorizontal", "-1"));
        appDockCount = Integer.parseInt(prefs.getString("appdockcount", "-1"));
        iconSize = Integer.parseInt(prefs.getString("iconsize", "100"));
        iconSizeAppDrawer = Integer.parseInt(prefs.getString("iconsizeappdrawer", "100"));
        appdockIconSize = Integer.parseInt(prefs.getString("appdockiconsize", "100"));
        iconTextSize = Integer.parseInt(prefs.getString("icontextsize", "100"));
        homescreenIconLabelColor = prefs.getInt("homescreeniconlabelcolor", Color.WHITE);
        appdrawerIconLabelColor = prefs.getInt("appdrawericonlabelcolor", Color.WHITE);
        appdrawerBackgroundColor = prefs.getInt("appdrawerbackgroundcolor", Color.argb(0xA5, 0x00, 0x00, 0x00));
        searchbarPrimaryColor = prefs.getInt("searchbarprimarycolor", Color.WHITE);
        appdrawerFolderStyleBackgroundColor = prefs.getInt("appdrawerfolderstylebackgroundcolor", Tab.DEFAULT_COLOR);
        appDockBackgroundColor = prefs.getInt("appdockbackgroundcolor", Color.argb(0x00, 0xFF, 0xFF, 0xFF));
        homescreenFolderColor = prefs.getInt("homescreenfoldercolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        homescreenFolderAppTextColor = prefs.getInt("homescreenfolderapptextcolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        homescreenFolderNameTextColor = prefs.getInt("homescreenfoldernametextcolor", Color.argb(0xFF, 0x77, 0x77, 0x77));
        homescreenFolderPreviewColor = prefs.getInt("homescreenfolderpreviewcolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        pageIndicatorColor = prefs.getInt("pageindicatorcolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        defaultHomescreen = Integer.parseInt(prefs.getString("defaulthomescreen", "-1"));
        workspaceRect = Integer.parseInt(prefs.getString("workspacerect", "1"));
        appdockRect = Integer.parseInt(prefs.getString("appdockrect", "1"));
        homescreenAllAppsPosition = Integer.parseInt(prefs.getString("positionallappsbutton", "-1"));
        smartFolderMode = Integer.parseInt(prefs.getString("smartfoldermode", "0"));
        scrolldevider = Integer.parseInt(prefs.getString("scrolldevider", "10"));
        searchbarStyle = Integer.parseInt(prefs.getString("searchbarstyle", "0"));
        contextmenuMode = Integer.parseInt(prefs.getString("contextmenumode", "3"));

        gesture_one_up_left = prefs.getString("gesture_one_up_left", "NONE");
        gesture_one_up_middle = prefs.getString("gesture_one_up_middle", "NONE");
        gesture_one_up_right = prefs.getString("gesture_one_up_right", "NONE");
        gesture_one_down_left = prefs.getString("gesture_one_down_left", "NONE");
        gesture_one_down_middle = prefs.getString("gesture_one_down_middle", "NONE");
        gesture_one_down_right = prefs.getString("gesture_one_down_right", "NONE");
        gesture_double_tap = prefs.getString("gesture_double_tap", "NONE");
        gesture_double_tap_only_on_wallpaper = prefs.getBoolean("gesture_double_tap_only_on_wallpaper", false);

        selectedIcons = prefs.getStringSet("selectedicons", new HashSet<String>());
        shortcutIcons = prefs.getStringSet("shortcuticons", new HashSet<String>());
        folderIcons = prefs.getStringSet("foldericons", new HashSet<String>());
        hiddenApps = prefs.getStringSet("hiddenapps", new HashSet<String>());
        hiddenWidgets = prefs.getStringSet("hiddenwidgets", new HashSet<String>());
        layerPositions = prefs.getStringSet("layerpositions", new HashSet<String>());
        appdrawerTabData = prefs.getStringSet("appdrawertabdata", new HashSet<String>());
        appdrawerFolderData = prefs.getStringSet("appdrawerfolderdata", new HashSet<String>());
        iconGestures = prefs.getStringSet("icongestures", new HashSet<String>());
        appNames = prefs.getStringSet("appnames", new HashSet<String>());

        iconpack = prefs.getString("iconpack", Common.ICONPACK_DEFAULT);
        iconPackAutoApply = prefs.getBoolean("autoupdateapplyiconpack", false);
        iconPackHide = prefs.getBoolean("hideiconpacks", false);

        notificationDialerApp = prefs.getString("notificationbadge_dialer_launch", "");
        notificationSMSApp = prefs.getString("notificationbadge_sms_launch", "");
        enableBadges = prefs.getBoolean("enablenotificationbadges", false);
        hideBadgesFromAppDrawer = prefs.getBoolean("hidenotificationbadgesappdrawer", false);
        notificationBadgePosition = Integer.parseInt(prefs.getString("notificationbadgeposition", "0"));
        notificationBadgeFrameSize = Integer.parseInt(prefs.getString("notificationbadgeframesize", "0"));
        notificationBadgeTextSize = Integer.parseInt(prefs.getString("notificationbadgetextsize", "10"));
        notificationBadgeCornerRadius = Integer.parseInt(prefs.getString("notificationbadgecornerradius", "5"));
        notificationBadgeLeftRightPadding = Integer.parseInt(prefs.getString("notificationbadgeleftrightpadding", "5"));
        notificationBadgeTopBottomPadding = Integer.parseInt(prefs.getString("notificationbadgetopbottompadding", "2"));
        notificationBadgeBackgroundColor = prefs.getInt("notificationbadgebackgroundcolor", Color.argb(0xA0, 0xD4, 0x49, 0x37));
        notificationBadgeTextColor = prefs.getInt("notificationbadgetextcolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        notificationBadgeFrameColor = prefs.getInt("notificationbadgeframecolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        notificationBadgeKeepSize = prefs.getBoolean("notificationbadgekeepsize", true);
        overrideSettingsButton = prefs.getBoolean("overridesettingsbutton", false);

        enableLLauncher = prefs.getBoolean("enablellauncher", false);
        hideWorkspaceShadow = prefs.getBoolean("hideworkspaceshadow", false);
        unlimitedFolderSize = prefs.getBoolean("unlimitedfoldersize", false);
        alwaysShowSayOKGoogle = prefs.getBoolean("alwaysshowsayokgoogle", false);
        glowColor = prefs.getInt("glowcolor", Color.argb(0xFF, 0xFF, 0xFF, 0xFF));
        transparentSystemBars = prefs.getBoolean("transparentsystembars", false);
        quicksettingsLockDesktop = prefs.getBoolean("quicksettingslockdesktop", false);
        enableAppDrawerTabs = prefs.getBoolean("enableappdrawertabs", false);
        appdrawerSwipeTabs = prefs.getBoolean("appdrawerswipetabs", false);
        moveTabHostBottom = prefs.getBoolean("movetabhostbottom", false);
        excludeAppsUsedInTabs = prefs.getBoolean("excludeappsusedintabs", false);
        autoHideHomeIcons = prefs.getBoolean("autohidehomeicons", false);

        pageIndicatorMode = prefs.getInt("pageindicatormode", hidePageIndicator ? 3 : 0);

        if (PreferencesHelper.Debug) XposedBridge.log("Initialized PreferencesHelper in " + (System.currentTimeMillis() - time) + "ms");
    }

    public static void initDefaultHomescreen() {
        if (PreferencesHelper.defaultHomescreen == -1) {
            boolean gnow = (Boolean) callMethod(Common.LAUNCHER_INSTANCE, ObfuscationHelper.Methods.lHasCustomContentToLeft);

            if (gnow) {
                PreferencesHelper.defaultHomescreen = 2;
            } else {
                PreferencesHelper.defaultHomescreen = 1;
            }

            if (Debug) XposedBridge.log("Setting default homescreen = " + PreferencesHelper.defaultHomescreen);
        }
    }
}