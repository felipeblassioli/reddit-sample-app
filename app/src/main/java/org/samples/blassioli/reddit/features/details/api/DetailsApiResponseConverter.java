package org.samples.blassioli.reddit.features.details.api;

import com.squareup.moshi.FromJson;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.samples.blassioli.reddit.api.posts.ListingOfLinks;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Reddit response's is a root array with two elements.
 * The first is of kind t3 (Link).
 * The second is of kind t1 (Comment)
 */
public class DetailsApiResponseConverter {

    private final JsonAdapter<Map<String, Object>> mapAdapter;

    private final JsonAdapter<ListingOfLinks> linkAdapter;

    private final JsonAdapter<ListingOfComments> commentAdapter;

    public DetailsApiResponseConverter() {
        Moshi moshi = new Moshi.Builder().build();

        Type type = Types.newParameterizedType(Map.class, String.class, Object.class);
        this.mapAdapter = moshi.adapter(type);
        this.linkAdapter = moshi.adapter(ListingOfLinks.class);
        this.commentAdapter = moshi.adapter(ListingOfComments.class);
    }

    @FromJson
    public DetailsApiResponse fromJson(List<Object> dataContainer) {
        DetailsApiResponse response = new DetailsApiResponse();
        Map<String, Object> data;
        String jsonStr;
        try {
            data = (Map<String, Object>) dataContainer.get(0);
            jsonStr = mapAdapter.toJson(data);
            response.childWithT3 = linkAdapter.fromJson(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            data = (Map<String, Object>) dataContainer.get(1);
            jsonStr = mapAdapter.toJson(data);
            response.childWithT1 = commentAdapter.fromJson(jsonStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
