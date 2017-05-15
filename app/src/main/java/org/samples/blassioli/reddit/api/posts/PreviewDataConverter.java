package org.samples.blassioli.reddit.api.posts;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.lang.reflect.Type;
import java.util.Map;

public class PreviewDataConverter {

    private final JsonAdapter<Map<String, Object>> mapJsonAdapter;

    private final JsonAdapter<PreviewData> previewDataAdapter;

    public PreviewDataConverter() {
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
        this.mapJsonAdapter = moshi.adapter(type);
        this.previewDataAdapter = moshi.adapter(PreviewData.class);
    }

    @FromJson
    public PreviewData fromJson(Object rawData) {
        if (rawData == null) {
            return null;
        }
        PreviewData response = null;
        Map<String, Object> data = null;
        try {
            data = (Map<String, Object>) rawData;
            String jsonStr = mapJsonAdapter.toJson(data);
            return previewDataAdapter.fromJson(jsonStr);
        } catch (Exception err) {
            err.printStackTrace();
        }
        return response;
    }
}
