package be.rubengerits.buildstatus.model.network;


import be.rubengerits.buildstatus.api.global.Authentication;
import be.rubengerits.buildstatus.api.global.Repositories;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitBuildStatusService {
    @POST("/api/authenticate")
    @Headers({
            "Accept:application/json",
            "Content-Type: application/json"
    })
    Observable<Authentication> authenticate(@Header("Authorization") String gitHubAuth);

    @GET("/api/repositories")
    @Headers({
            "Accept:application/json",
            "Content-Type: application/json"
    })
    Observable<Repositories> getRepositories(@Header("Authorization") String travisCiAuth);

}

