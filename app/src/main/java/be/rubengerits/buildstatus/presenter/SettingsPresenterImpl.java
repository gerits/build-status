package be.rubengerits.buildstatus.presenter;

import android.content.Context;
import android.database.Cursor;

import com.squareup.sqlbrite.SqlBrite;

import java.util.ArrayList;
import java.util.List;

import be.rubengerits.buildstatus.model.database.DataBaseHelper;
import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.AccountType;
import be.rubengerits.buildstatus.model.network.BuildStatusServiceImpl;
import be.rubengerits.buildstatus.model.data.AccessToken;
import be.rubengerits.buildstatus.view.SettingsView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SettingsPresenterImpl implements SettingsPresenter {

    private final DataBaseHelper dataBaseHelper;
    private final BuildStatusServiceImpl buildStatusService;
    private SettingsView view;

    public SettingsPresenterImpl(SettingsView view, Context context) {
        this.view = view;
        dataBaseHelper = new DataBaseHelper(context);
        buildStatusService = new BuildStatusServiceImpl();
    }

    @Override
    public void loadAllAccounts() {
        dataBaseHelper.getAccounts()
                .doOnError(e -> view.showError(e))
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
        buildStatusService.authenticate(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(throwable -> null)
                .doOnError(e -> view.showError(e))
                .doOnNext(accessToken -> saveAccessToken(type, username, accessToken))
                .subscribe();
    }

    private void saveAccessToken(AccountType type, String username, AccessToken accessToken) {
        if (accessToken != null && accessToken.getAccessToken() != null && !"undefined".equals(accessToken.getAccessToken())) {
            Account account = new Account();
            account.setUsername(username);
            account.setToken(accessToken.getAccessToken());
            account.setType(type);
            dataBaseHelper.saveAccount(account);

            loadAllAccounts();
        }
    }
}
