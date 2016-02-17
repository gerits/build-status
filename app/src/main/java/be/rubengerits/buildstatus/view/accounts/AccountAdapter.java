package be.rubengerits.buildstatus.view.accounts;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.Account;

public class AccountAdapter extends RecyclerView.Adapter<AccountViewHolder> {

    private List<Account> accounts = new ArrayList<>();

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        holder.bind(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
        notifyDataSetChanged();
    }

}
