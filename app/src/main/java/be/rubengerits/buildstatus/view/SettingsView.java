package be.rubengerits.buildstatus.view;

import java.util.List;

import be.rubengerits.buildstatus.model.data.Account;

public interface SettingsView extends ErrorHandlerView {
    void showContent(List<Account> accounts);
}
