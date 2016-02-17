package be.rubengerits.buildstatus.model.data;

import android.support.annotation.ColorInt;

import java.util.Arrays;
import java.util.List;

import be.rubengerits.buildstatus.R;

public class BuildStatus {

    public static final BuildStatus STATUS_LOGIN = new BuildStatus(Arrays.asList("login"), R.drawable.ic_background_login, null, R.color.colorLogin, R.color.colorLoginDark, 0);
    public static final BuildStatus STATUS_FAILING = new BuildStatus(Arrays.asList("failed", "errored", "canceled"), R.drawable.ic_background_failing, R.drawable.ic_failing_16dp, R.color.colorFailing, R.color.colorFailingDark, 10);
    public static final BuildStatus STATUS_BUILDING = new BuildStatus(Arrays.asList("created", "started", "received", "queued"), R.drawable.ic_background_building, R.drawable.ic_building_16dp, R.color.colorBuilding, R.color.colorBuildingDark, 9);
    public static final BuildStatus STATUS_PASSING = new BuildStatus(Arrays.asList("passed", "ready"), R.drawable.ic_background_passing, R.drawable.ic_passing_16dp, R.color.colorPassing, R.color.colorPassingDark, 8);
    public static final BuildStatus STATUS_UNKNOWN = new BuildStatus(null, R.drawable.ic_background_unknown, R.drawable.ic_unknown_16dp, R.color.colorUnknown, R.color.colorUnknownDark, 0);

    private List<String> names;

    private final int resourceId;

    private final Integer iconId;

    @ColorInt
    private final int colorPrimary;

    @ColorInt
    private final int colorPrimaryDark;

    private final int weight;

    private BuildStatus(List<String> names, int resourceId, Integer iconId, int colorPrimary, int colorPrimaryDark, int weight) {
        this.names = names;
        this.resourceId = resourceId;
        this.iconId = iconId;
        this.colorPrimary = colorPrimary;
        this.colorPrimaryDark = colorPrimaryDark;
        this.weight = weight;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getColorPrimary() {
        return colorPrimary;
    }

    public int getColorPrimaryDark() {
        return colorPrimaryDark;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getNames() {
        return names;
    }

    public String getName() {
        if (names != null && names.size() > 0) {
            return names.get(0);
        }
        return null;
    }

    public Integer getIconId() {
        return iconId;
    }

    public static BuildStatus valueOf(String name) {
        if (STATUS_LOGIN.getNames().contains(name)) {
            return STATUS_LOGIN;
        } else if (STATUS_BUILDING.getNames().contains(name)) {
            return STATUS_BUILDING;
        } else if (STATUS_FAILING.getNames().contains(name)) {
            return STATUS_FAILING;
        } else if (STATUS_PASSING.getNames().contains(name)) {
            return STATUS_PASSING;
        }

        return BuildStatus.STATUS_UNKNOWN;
    }
}
