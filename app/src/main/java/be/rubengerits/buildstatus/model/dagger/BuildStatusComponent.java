package be.rubengerits.buildstatus.model.dagger;

import javax.inject.Singleton;

import be.rubengerits.buildstatus.presenter.OverviewPresenterImpl;
import be.rubengerits.buildstatus.presenter.SettingsPresenterImpl;
import dagger.Component;

@Singleton
@Component(modules = BuildStatusModule.class)
public interface BuildStatusComponent {

    void inject(SettingsPresenterImpl presenter);

    void inject(OverviewPresenterImpl presenter);

}
