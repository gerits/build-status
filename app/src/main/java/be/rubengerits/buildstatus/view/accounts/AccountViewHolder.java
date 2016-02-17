package be.rubengerits.buildstatus.view.accounts;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.Account;

public class AccountViewHolder extends RecyclerView.ViewHolder {
    private final TextView accountName;

    public AccountViewHolder(View itemView) {
        super(itemView);

        accountName = (TextView) itemView.findViewById(R.id.account_name);
    }

    public void bind(Account account) {
        accountName.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(accountName.getContext(), account.getType().getIcon()), null, null, null);
        accountName.setText(account.getUsername());
    }

}
