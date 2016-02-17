package be.rubengerits.buildstatus.view.repositories;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.Repository;

public class RepositoriesAdapter extends RecyclerView.Adapter<RepositoryViewHolder> {

    private List<Repository> repositories = new ArrayList<>();

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repository, parent, false);
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RepositoryViewHolder holder, int position) {
        holder.bind(repositories.get(position));
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }

    public void setRepositories(Set<Repository> repos) {
        this.repositories.clear();
        this.repositories.addAll(repos);
        notifyDataSetChanged();
    }

}
