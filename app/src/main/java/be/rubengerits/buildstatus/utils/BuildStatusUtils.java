package be.rubengerits.buildstatus.utils;

import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;

import java.util.HashMap;
import java.util.Map;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.api.global.BuildStatus;

public class BuildStatusUtils {

    private static final Map<BuildStatus, Integer> color = new HashMap<>();

    static {
        color.put(BuildStatus.STATUS_LOGIN, R.color.colorLogin);
        color.put(BuildStatus.STATUS_FAILING, R.color.colorFailing);
        color.put(BuildStatus.STATUS_BUILDING, R.color.colorBuilding);
        color.put(BuildStatus.STATUS_PASSING, R.color.colorPassing);
        color.put(BuildStatus.STATUS_UNKNOWN, R.color.colorUnknown);
    }

    private static final Map<BuildStatus, Integer> colorDark = new HashMap<>();

    static {
        colorDark.put(BuildStatus.STATUS_LOGIN, R.color.colorLoginDark);
        colorDark.put(BuildStatus.STATUS_FAILING, R.color.colorFailingDark);
        colorDark.put(BuildStatus.STATUS_BUILDING, R.color.colorBuildingDark);
        colorDark.put(BuildStatus.STATUS_PASSING, R.color.colorPassingDark);
        colorDark.put(BuildStatus.STATUS_UNKNOWN, R.color.colorUnknownDark);
    }

    private static final Map<BuildStatus, Integer> backgroundImage = new HashMap<>();

    static {
        backgroundImage.put(BuildStatus.STATUS_LOGIN, R.drawable.ic_background_login);
        backgroundImage.put(BuildStatus.STATUS_FAILING, R.drawable.ic_background_failing);
        backgroundImage.put(BuildStatus.STATUS_BUILDING, R.drawable.ic_background_building);
        backgroundImage.put(BuildStatus.STATUS_PASSING, R.drawable.ic_background_passing);
        backgroundImage.put(BuildStatus.STATUS_UNKNOWN, R.drawable.ic_background_unknown);
    }

    private static final Map<BuildStatus, Integer> icon = new HashMap<>();

    static {
        icon.put(BuildStatus.STATUS_LOGIN, R.drawable.ic_status_unknown);
        icon.put(BuildStatus.STATUS_FAILING, R.drawable.ic_status_error);
        icon.put(BuildStatus.STATUS_BUILDING, R.drawable.ic_status_building_anim);
        icon.put(BuildStatus.STATUS_PASSING, R.drawable.ic_status_passing);
        icon.put(BuildStatus.STATUS_UNKNOWN, R.drawable.ic_status_unknown);
    }

    @ColorInt
    public static int getColor(BuildStatus buildStatus) {
        return color.get(buildStatus);
    }

    @ColorInt
    public static int getColorDark(BuildStatus buildStatus) {
        return colorDark.get(buildStatus);
    }

    @IdRes
    public static int getBackgroundImage(BuildStatus buildStatus) {
        return backgroundImage.get(buildStatus);
    }

    @IdRes
    public static int getIcon(BuildStatus buildStatus) {
        return icon.get(buildStatus);
    }
}
