package be.rubengerits.buildstatus.model.network;

import be.rubengerits.buildstatus.api.global.Authentication;
import be.rubengerits.buildstatus.api.global.Repositories;
import be.rubengerits.buildstatus.model.data.Account;
import rx.Observable;

public interface BuildStatusService {
    Observable<Authentication> authenticate(String username, String password);

    Observable<Repositories> getRepositories(Account account);
}
