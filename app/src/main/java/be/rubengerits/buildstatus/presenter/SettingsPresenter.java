package be.rubengerits.buildstatus.presenter;

import be.rubengerits.buildstatus.model.data.AccountType;

public interface SettingsPresenter {
    void loadAllAccounts();

    void connectUser(AccountType type, String username, String password);
}
