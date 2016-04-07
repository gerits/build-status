package be.rubengerits.buildstatus.presenter;

import android.content.Context;
import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import be.rubengerits.buildstatus.api.global.Authentication;
import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.AccountType;
import be.rubengerits.buildstatus.model.database.DataBaseHelper;
import be.rubengerits.buildstatus.model.network.BuildStatusService;
import be.rubengerits.buildstatus.view.SettingsView;
import dagger.Lazy;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsPresenterImpl implements SettingsPresenter {

    @Inject
    DataBaseHelper dataBaseHelper;
    @Inject
    Lazy<BuildStatusService> buildStatusService;

    private SettingsView view;

    public SettingsPresenterImpl(SettingsView view, Context context) {
        this.view = view;
    }

    @Override
    public void loadAllAccounts() {
        dataBaseHelper.getAccounts()
                .doOnError(view::showError)
                .doOnNext(this::handleAccount)
                .subscribe();
    }

    private void handleAccount(SqlBrite.Query query) {
        Cursor run = query.run();
        List<Account> accounts = new ArrayList<>();
        if (run.getCount() > 0) {
            run.moveToFirst();
            do {
                Account account = new Account();
                account.setId(run.getInt(run.getColumnIndex("id")));
                account.setUsername(run.getString(run.getColumnIndex("username")));
                account.setToken(run.getString(run.getColumnIndex("token")));
                account.setType(AccountType.valueOf(run.getString(run.getColumnIndex("type"))));
                accounts.add(account);
            } while (run.moveToNext());
        }
        run.close();

        view.showContent(accounts);
    }

    @Override
    public void connectUser(final AccountType type, final String username, final String password) {
        buildStatusService.get().authenticate(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> null)
                .doOnError(view::showError)
                .doOnNext(authentication -> saveAccessToken(type, username, authentication))
                .subscribe();
    }

    private void saveAccessToken(AccountType type, String username, Authentication authentication) {
        if (authentication != null && authentication.getAccessToken() != null && !"undefined".equals(authentication.getAccessToken())) {
            Account account = new Account();
            account.setUsername(username);
            account.setToken(authentication.getAccessToken());
            account.setType(type);
            dataBaseHelper.saveAccount(account);

            loadAllAccounts();
        }
    }

}
