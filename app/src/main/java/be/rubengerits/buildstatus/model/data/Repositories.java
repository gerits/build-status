package be.rubengerits.buildstatus.model.data;

import java.util.ArrayList;
import java.util.List;

import be.rubengerits.buildstatus.model.data.Repository;

public class Repositories {

    private List<Repository> repositories = new ArrayList<>();

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }
}
