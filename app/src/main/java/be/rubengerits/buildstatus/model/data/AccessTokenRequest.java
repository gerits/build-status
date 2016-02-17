package be.rubengerits.buildstatus.model.data;

public class AccessTokenRequest {

    private String githubToken;

    public AccessTokenRequest(String githubToken) {
        this.githubToken = githubToken;
    }

    public String getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(String githubToken) {
        this.githubToken = githubToken;
    }
}
