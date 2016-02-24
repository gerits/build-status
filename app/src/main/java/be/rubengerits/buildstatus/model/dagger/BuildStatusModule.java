package be.rubengerits.buildstatus.model.dagger;

import android.content.Context;

import javax.inject.Singleton;

import be.rubengerits.buildstatus.model.database.DataBaseHelper;
import be.rubengerits.buildstatus.model.database.DataBaseHelperImpl;
import be.rubengerits.buildstatus.model.network.BuildStatusService;
import be.rubengerits.buildstatus.model.network.BuildStatusServiceImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class BuildStatusModule {

    private Context context;
    private String url;

    public BuildStatusModule(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    @Provides
    @Singleton
    public BuildStatusService provideBuildStatusService() {
        return new BuildStatusServiceImpl(url);
    }

    @Provides
    @Singleton
    public DataBaseHelper provideDataBaseHelper() {
        return new DataBaseHelperImpl(context);
    }

}
