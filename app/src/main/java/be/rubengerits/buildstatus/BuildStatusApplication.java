package be.rubengerits.buildstatus;

import android.app.Application;

import be.rubengerits.buildstatus.model.dagger.BuildStatusComponent;
import be.rubengerits.buildstatus.model.dagger.BuildStatusModule;
import be.rubengerits.buildstatus.model.dagger.DaggerBuildStatusComponent;

public class BuildStatusApplication extends Application {

    public static final String API_URL = "https://buildstatus-gerits.rhcloud.com";

    private final BuildStatusComponent buildStatusComponent = createBuildStatusComponent();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    protected BuildStatusComponent createBuildStatusComponent() {
        return DaggerBuildStatusComponent.builder()
                .buildStatusModule(new BuildStatusModule(this, API_URL))
                .build();
    }

    public BuildStatusComponent getBuildStatusComponent() {
        return buildStatusComponent;
    }
}
