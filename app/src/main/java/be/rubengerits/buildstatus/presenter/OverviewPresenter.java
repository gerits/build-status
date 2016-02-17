package be.rubengerits.buildstatus.presenter;

import rx.Observable;

public interface OverviewPresenter {
    void updateRepositories();

    Observable<Boolean> isAuthenticated();
}
