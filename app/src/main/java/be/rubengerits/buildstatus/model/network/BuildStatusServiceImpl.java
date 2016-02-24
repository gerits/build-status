package be.rubengerits.buildstatus.model.network;


import android.util.Base64;

import javax.inject.Inject;

import be.rubengerits.buildstatus.model.data.AccessToken;
import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.Repositories;
import retrofit2.Retrofit;
import rx.Observable;

public class BuildStatusServiceImpl implements BuildStatusService {
    public final String url;

    @Inject
    public BuildStatusServiceImpl(String url) {
        this.url = url;
    }

    @Override
    public Observable<AccessToken> authenticate(String username, String password) {
        Retrofit retrofit = RestUtils.createRetrofit(url);

        RetrofitBuildStatusService service = retrofit.create(RetrofitBuildStatusService.class);

        String basicAuth = generateBasicAuth(username, password);

        return service.authenticate(basicAuth);
    }

    @Override
    public Observable<Repositories> getRepositories(Account account) {
        Retrofit retrofit = RestUtils.createRetrofit(url);

        RetrofitBuildStatusService service = retrofit.create(RetrofitBuildStatusService.class);

        return service.getRepositories("token " + account.getToken());
    }

    private String generateBasicAuth(String username, String password) {
        String credentials = String.format("%1$s:%2$s", username, password);
        String basicAuth = Base64.encodeToString(credentials.getBytes(), Base64.DEFAULT);
        return String.format("Basic %1$s", basicAuth.substring(0, basicAuth.length() - 1));
    }

}
