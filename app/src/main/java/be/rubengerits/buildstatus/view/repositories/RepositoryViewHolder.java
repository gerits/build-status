package be.rubengerits.buildstatus.view.repositories;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import be.rubengerits.buildstatus.R;
import be.rubengerits.buildstatus.model.data.BuildStatus;
import be.rubengerits.buildstatus.model.data.Repository;
import be.rubengerits.buildstatus.utils.DateUtils;

public class RepositoryViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView repositoryName, buildDuration, buildFinished;
    private final View buildStatus;

    public RepositoryViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();

        buildStatus = itemView.findViewById(R.id.build_status);

        repositoryName = (TextView) itemView.findViewById(R.id.repository_name);
        buildDuration = (TextView) itemView.findViewById(R.id.build_duration);
        buildFinished = (TextView) itemView.findViewById(R.id.build_finished);
    }

    public void bind(Repository repo) {
        repositoryName.setText(repo.getName());
        setLastBuildDuration(repo);
        setLastBuildFinished(repo);

        BuildStatus lastBuildState = repo.getLastBuildState();
        buildStatus.setBackgroundColor(ContextCompat.getColor(context, lastBuildState.getColorPrimary()));
        repositoryName.setCompoundDrawablesWithIntrinsicBounds(lastBuildState.getIconId() != null ? ContextCompat.getDrawable(context, lastBuildState.getIconId()) : null, null, null, null);
        repositoryName.setTextColor(ContextCompat.getColor(context, lastBuildState.getColorPrimary()));
    }

    private void setLastBuildFinished(Repository repo) {
        long now = new Date().getTime();
        Date lastBuildFinishedAt = repo.getLastBuildFinishedAt();
        if (lastBuildFinishedAt != null) {
            buildFinished.setText(context.getString(R.string.build_finished, DateUtils.getRelativeTimeSpanString(lastBuildFinishedAt.getTime(), now, DateUtils.SECOND_IN_MILLIS)));
        } else {
            buildFinished.setText(context.getString(R.string.build_finished, "-"));
        }
    }

    private void setLastBuildDuration(Repository repo) {
        Long lastBuildDuration = repo.getLastBuildDuration();
        if (lastBuildDuration != null) {
            buildDuration.setText(context.getString(R.string.build_duration, DateUtils.formatElapsedTime(context, lastBuildDuration)));
        } else {
            buildDuration.setText(context.getString(R.string.build_duration, "-"));
        }
    }

}
