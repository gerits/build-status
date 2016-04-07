package be.rubengerits.buildstatus.presenter;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import be.rubengerits.buildstatus.api.global.Repositories;
import be.rubengerits.buildstatus.api.global.Repository;
import be.rubengerits.buildstatus.model.data.Account;
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

        if (dataBaseHelper.hasAccounts()) {
            dataBaseHelper.getAccounts()
                    .flatMap(this::loadAccounts)
                    .flatMap(buildStatusService::getRepositories)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnCompleted(view::stopLoading)
                    .doOnError(view::showError)
                    .onErrorReturn(e -> new Repositories())
                    .doOnNext(repositories -> {
                        if (repositories.getRepositories() != null) {
                            repositoryList.addAll(repositories.getRepositories());
                        }
                        view.showContent(repositoryList);
                        view.stopLoading();
                    })
                    .subscribe();
        } else {
            view.showNoAccounts();
        }
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
