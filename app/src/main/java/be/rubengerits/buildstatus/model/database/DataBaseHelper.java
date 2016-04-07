package be.rubengerits.buildstatus.model.database;

import com.squareup.sqlbrite.QueryObservable;

import be.rubengerits.buildstatus.api.global.Repository;
import be.rubengerits.buildstatus.model.data.Account;

public interface DataBaseHelper {
    QueryObservable getAccounts();

    void saveAccount(Account account);

    QueryObservable getRepositories();

    void saveRepository(Repository repository);

    void removeAllRepositories();

    boolean hasAccounts();
}
