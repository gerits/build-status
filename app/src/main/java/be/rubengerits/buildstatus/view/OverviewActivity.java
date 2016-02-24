package be.rubengerits.buildstatus.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.frakbot.jumpingbeans.JumpingBeans;

import java.util.Set;
import java.util.TreeSet;

import be.rubengerits.buildstatus.BuildStatusApplication;
import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.BuildStatus;
import be.rubengerits.buildstatus.model.data.Repository;
import be.rubengerits.buildstatus.presenter.OverviewPresenterImpl;
import be.rubengerits.buildstatus.view.repositories.RepositoriesAdapter;
import be.rubengerits.buildstatus.view.widget.EmptyRecyclerView;

public class OverviewActivity extends AppCompatActivity implements OverviewView {

    EmptyRecyclerView buildList;

    private OverviewPresenterImpl presenter;
    private RepositoriesAdapter repositoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        buildList = (EmptyRecyclerView) findViewById(R.id.build_list);

        presenter = new OverviewPresenterImpl(this);
        ((BuildStatusApplication) getApplication()).getBuildStatusComponent().inject(presenter);

        repositoriesAdapter = new RepositoriesAdapter();
        buildList.setAdapter(repositoriesAdapter);

        updateStatus(BuildStatus.STATUS_UNKNOWN);
        buildList.setEmptyView(findViewById(R.id.layout_no_data));
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.updateRepositories();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_refresh) {
            presenter.updateRepositories();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStatus(BuildStatus status) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setBackground(ContextCompat.getDrawable(this, status.getResourceId()));
        toolbarLayout.setContentScrimColor(ContextCompat.getColor(this, status.getColorPrimary()));
        toolbarLayout.setStatusBarScrimColor(ContextCompat.getColor(this, status.getColorPrimaryDark()));
    }

    /**
     * presenter methods
     */

    @Override
    public void startLoading() {
        hideError();
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getString(R.string.loading));
        JumpingBeans.with(titleView)
                .appendJumpingDots()
                .build();
    }

    @Override
    public void stopLoading() {
        TextView titleView = (TextView) findViewById(R.id.title);
        titleView.setText(getString(R.string.app_name));
    }

    @Override
    public void showContent(Set<Repository> repositories) {
        updateView(getGeneralStatus(repositories), R.id.layout_no_data, repositories);
    }

    @Override
    public void showNoAccounts() {
        updateView(BuildStatus.STATUS_LOGIN, R.id.layout_no_accounts, new TreeSet<>());
    }

    @Override
    public void showError(Throwable t) {
        stopLoading();
        Log.e("OVERVIEW_ERROR", t.getMessage(), t);
        findViewById(R.id.layout_error).setVisibility(View.VISIBLE);
    }

    public void hideError() {
        findViewById(R.id.layout_error).setVisibility(View.GONE);
    }

    private void updateView(BuildStatus status, int emptyLayout, Set<Repository> repositories) {
        updateStatus(status);
        buildList.setEmptyView(findViewById(emptyLayout));
        repositoriesAdapter.setRepositories(repositories);
    }

    private BuildStatus getGeneralStatus(Set<Repository> repositories) {
        BuildStatus status = BuildStatus.STATUS_UNKNOWN;
        for (Repository repository : repositories) {
            BuildStatus repositoryStatus = repository.getLastBuildState();
            if (repositoryStatus.getWeight() > status.getWeight()) {
                status = repositoryStatus;
            }
        }
        return status;
    }

}
