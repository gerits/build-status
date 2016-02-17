package be.rubengerits.buildstatus.view;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.AccountType;
import be.rubengerits.buildstatus.presenter.SettingsPresenter;
import be.rubengerits.buildstatus.presenter.SettingsPresenterImpl;
import be.rubengerits.buildstatus.view.accounts.AccountAdapter;
import be.rubengerits.buildstatus.view.widget.EmptyRecyclerView;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    private SettingsPresenter presenter;

    EmptyRecyclerView accountList;
    private AccountAdapter accountAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();

        setContentView(R.layout.activity_settings);

        presenter = new SettingsPresenterImpl(this, this);

        accountList = (EmptyRecyclerView) findViewById(R.id.account_list);

        accountAdapter = new AccountAdapter();
        accountList.setAdapter(accountAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadAllAccounts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (id == R.id.action_add_account) {
            showAccountAddDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAccountAddDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_account, null);

        View username = dialogView.findViewById(R.id.account_username);
        username.requestFocus();

        dialog.setTitle(R.string.action_add_account);
        dialog.setPositiveButton(android.R.string.ok, (dialog1, which) -> {
            EditText username1 = (EditText) dialogView.findViewById(R.id.account_username);
            EditText password = (EditText) dialogView.findViewById(R.id.account_password);

            presenter.connectUser(AccountType.TRAVIS_CI, username1.getText().toString(), password.getText().toString());

            dialog1.dismiss();
        });
        dialog.setNegativeButton(android.R.string.cancel, (dialog1, which) -> dialog1.cancel());
        dialog.setView(dialogView);
        dialog.show();
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContent(List<Account> accounts) {
        accountAdapter.setAccounts(accounts);
    }
}
