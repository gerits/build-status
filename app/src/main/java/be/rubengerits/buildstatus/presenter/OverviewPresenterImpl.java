package be.rubengerits.buildstatus.presenter;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.BuildStatus;
import be.rubengerits.buildstatus.model.data.Repositories;
import be.rubengerits.buildstatus.model.data.Repository;
import be.rubengerits.buildstatus.model.database.DataBaseHelper;
import be.rubengerits.buildstatus.model.network.BuildStatusService;
import be.rubengerits.buildstatus.view.OverviewView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OverviewPresenterImpl implements OverviewPresenter {

    @Inject
    BuildStatusService buildStatusService;
    @Inject
    DataBaseHelper dataBaseHelper;
    private OverviewView view;

    public OverviewPresenterImpl(OverviewView view) {
        this.view = view;
    }

    public void updateRepositories() {
        view.startLoading();
        final Set<Repository> repositoryList = new TreeSet<>();
        dataBaseHelper.getRepositories().flatMap(this::loadRepositories)
                .mergeWith(dataBaseHelper.getAccounts().flatMap(this::loadAccounts).flatMap(buildStatusService::getRepositories))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnCompleted(view::stopLoading)
                .doOnError(view::showError)
                .onErrorReturn(e -> new Repositories())
                .doOnNext(repositories -> {
                    repositoryList.addAll(repositories.getRepositories());

                    dataBaseHelper.removeAllRepositories();
                    for (Repository repository : repositories.getRepositories()) {
                        dataBaseHelper.saveRepository(repository);
                    }

                    view.showContent(repositoryList);
                    view.stopLoading();
                })
                .subscribe();
    }

    private Observable<Repositories> loadRepositories(SqlBrite.Query query) {
        Repositories repositories = new Repositories();
        if (query != null) {
            try (Cursor run = query.run()) {
                if (run.getCount() > 0) {
                    run.moveToFirst();
                    do {
                        Repository repository = new Repository();
                        repository.setId(run.getString(run.getColumnIndex("id")));
                        repository.setName(run.getString(run.getColumnIndex("name")));
                        repository.setDescription(run.getString(run.getColumnIndex("description")));
                        repository.setLastBuildNumber(run.getString(run.getColumnIndex("lastBuildNumber")));
                        repository.setLastBuildState(BuildStatus.valueOf(run.getString(run.getColumnIndex("lastBuildState"))));
                        repository.setLastBuildDuration(run.getLong(run.getColumnIndex("lastBuildDuration")));

                        Calendar finished = Calendar.getInstance();
                        finished.setTimeInMillis(run.getLong(run.getColumnIndex("lastBuildFinished")));
                        repository.setLastBuildFinishedAt(finished.getTime());

                        repositories.getRepositories().add(repository);
                    } while (run.moveToNext());
                }
            }
        }
        return Observable.from(Arrays.asList(repositories));

    }

    @NonNull
    private Observable<Account> loadAccounts(SqlBrite.Query query) {
        List<Account> accounts = new ArrayList<>();
        if (query != null) {
            try (Cursor run = query.run()) {
                if (run.getCount() > 0) {
                    run.moveToFirst();
                    do {
                        Account account = new Account();
                        account.setId(run.getInt(run.getColumnIndex("id")));
                        account.setUsername(run.getString(run.getColumnIndex("username")));
                        account.setToken(run.getString(run.getColumnIndex("token")));
                        accounts.add(account);
                    } while (run.moveToNext());
                }
            }
        }
        return Observable.from(accounts);
    }

    public Observable<Boolean> isAuthenticated() {
        return Observable.from(new Boolean[]{Boolean.TRUE});
    }
}
