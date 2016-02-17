package be.rubengerits.buildstatus.model.data;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class BuildStatusJsonAdapter extends TypeAdapter<BuildStatus> {
    @Override
    public void write(JsonWriter out, BuildStatus value) throws IOException {
        out.value(value.getName());
    }

    @Override
    public BuildStatus read(JsonReader in) throws IOException {
        return BuildStatus.valueOf(in.nextString());
    }
}
