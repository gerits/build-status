package be.rubengerits.buildstatus.view;

import java.util.Set;

import be.rubengerits.buildstatus.model.data.Repository;

public interface OverviewView extends ErrorHandlerView {
    void startLoading();

    void stopLoading();

    void showContent(Set<Repository> repositories);

    void showNoAccounts();
}
