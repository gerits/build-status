package be.rubengerits.buildstatus.model.data;

import be.rubengerits.buildstatus.R;

public class AccountType {

    public static final AccountType TRAVIS_CI = new AccountType("travis-ci", R.drawable.ic_account_travisci);

    private final String id;
    private final int icon;

    private AccountType(String id, int icon) {
        this.id = id;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return getId();
    }

    public static AccountType valueOf(String id) {
        if (TRAVIS_CI.getId().equals(id)) {
            return TRAVIS_CI;
        }
        throw new IllegalArgumentException();
    }
}
