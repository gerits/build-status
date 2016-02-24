package be.rubengerits.buildstatus.model.database;

import android.content.Context;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import be.rubengerits.buildstatus.model.data.Account;
import be.rubengerits.buildstatus.model.data.Repository;

public class DataBaseHelperImpl implements DataBaseHelper {

    private Context context;

    @Inject
    public DataBaseHelperImpl(Context context) {
        this.context = context;
    }

    @Override
    public QueryObservable getAccounts() {
        SqlBrite sqlBrite = SqlBrite.create();
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new BuildStatusOpenHelper(context));

        return db.createQuery(BuildStatusOpenHelper.TABLE_ACCOUNTS, "SELECT * FROM " + BuildStatusOpenHelper.TABLE_ACCOUNTS);
    }

    @Override
    public void saveAccount(Account account) {
        SqlBrite sqlBrite = SqlBrite.create();

        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new BuildStatusOpenHelper(context));

        try (BriteDatabase.Transaction transaction = db.newTransaction()) {
            db.insert(BuildStatusOpenHelper.TABLE_ACCOUNTS, account.toDatabase());
            transaction.markSuccessful();
        }
    }

    @Override
    public QueryObservable getRepositories() {
        SqlBrite sqlBrite = SqlBrite.create();
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new BuildStatusOpenHelper(context));

        return db.createQuery(BuildStatusOpenHelper.TABLE_REPOSITORIES, "SELECT * FROM " + BuildStatusOpenHelper.TABLE_REPOSITORIES);
    }

    @Override
    public void saveRepository(Repository repository) {
        SqlBrite sqlBrite = SqlBrite.create();

        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new BuildStatusOpenHelper(context));

        try (BriteDatabase.Transaction transaction = db.newTransaction()) {
            int update = db.update(BuildStatusOpenHelper.TABLE_REPOSITORIES, repository.toDatabase(), "name LIKE '" + repository.getName() + "'");
            if (update == 0) {
                db.insert(BuildStatusOpenHelper.TABLE_REPOSITORIES, repository.toDatabase());
            }
            transaction.markSuccessful();
        } catch (Exception e) {
            Log.e("dbfail", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void removeAllRepositories() {
        SqlBrite sqlBrite = SqlBrite.create();

        BriteDatabase db = sqlBrite.wrapDatabaseHelper(new BuildStatusOpenHelper(context));

        try (BriteDatabase.Transaction transaction = db.newTransaction()) {
            db.delete(BuildStatusOpenHelper.TABLE_REPOSITORIES, "name LIKE '*'");
            transaction.markSuccessful();
        } catch (Exception e) {
            Log.e("dbfail", e.getMessage(), e);
            throw e;
        }
    }
}
