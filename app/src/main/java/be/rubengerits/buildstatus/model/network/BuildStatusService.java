package be.rubengerits.buildstatus.model.network;

import be.rubengerits.buildstatus.model.data.AccessToken;
import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.Repositories;
import rx.Observable;

public interface BuildStatusService {
    Observable<AccessToken> authenticate(String username, String password);

    Observable<Repositories> getRepositories(Account account);
}
