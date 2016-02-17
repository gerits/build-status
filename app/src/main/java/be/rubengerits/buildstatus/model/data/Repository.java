package be.rubengerits.buildstatus.model.data;


import android.content.ContentValues;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Repository implements Comparable<Repository> {
    private String id;

    private String name;

    private String description;

    @SerializedName("last_build_number")
    private String lastBuildNumber;

    @JsonAdapter(BuildStatusJsonAdapter.class)
    @SerializedName("last_build_state")
    private BuildStatus lastBuildState;

    @SerializedName("last_build_duration")
    private Long lastBuildDuration;

    @SerializedName("last_build_finished_at")
    private Date lastBuildFinishedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BuildStatus getLastBuildState() {
        return lastBuildState;
    }

    public void setLastBuildState(BuildStatus lastBuildState) {
        this.lastBuildState = lastBuildState;
    }

    public Long getLastBuildDuration() {
        return lastBuildDuration;
    }

    public void setLastBuildDuration(Long lastBuildDuration) {
        this.lastBuildDuration = lastBuildDuration;
    }

    public Date getLastBuildFinishedAt() {
        return lastBuildFinishedAt;
    }

    public void setLastBuildFinishedAt(Date lastBuildFinishedAt) {
        this.lastBuildFinishedAt = lastBuildFinishedAt;
    }

    public String getLastBuildNumber() {
        return lastBuildNumber;
    }

    public void setLastBuildNumber(String lastBuildNumber) {
        this.lastBuildNumber = lastBuildNumber;
    }

    public ContentValues toDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("description", description);
        contentValues.put("lastBuildNumber", lastBuildNumber);
        contentValues.put("lastBuildState", lastBuildState.getName());
        contentValues.put("lastBuildDuration", lastBuildDuration);
        contentValues.put("lastBuildFinished", lastBuildFinishedAt.getTime());
        return contentValues;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Repository) {
            Repository repository = (Repository) o;

            repository.getName().equals(getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public int compareTo(Repository another) {
        if (another == null) {
            return -1;
        }
        return another.getLastBuildFinishedAt().compareTo(getLastBuildFinishedAt());
    }
}
