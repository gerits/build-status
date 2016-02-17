package be.rubengerits.buildstatus.model.network;


import be.rubengerits.buildstatus.model.data.AccessToken;
import be.rubengerits.buildstatus.model.data.Repositories;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface BuildStatusService {
    @POST("/api/authenticate")
    Observable<AccessToken> authenticate(@Header("Authorization") String gitHubAuth);

    @GET("/api/repos")
    Observable<Repositories> getRepositories(@Header("Authorization") String travisCiAuth);

}
