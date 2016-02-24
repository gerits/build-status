package be.rubengerits.buildstatus.model.database;

import com.squareup.sqlbrite.QueryObservable;

import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.Repository;

/**
 * Created by GERIR on 20/02/2016.
 */
public interface DataBaseHelper {
    QueryObservable getAccounts();

    void saveAccount(Account account);

    QueryObservable getRepositories();

    void saveRepository(Repository repository);

    void removeAllRepositories();
}
