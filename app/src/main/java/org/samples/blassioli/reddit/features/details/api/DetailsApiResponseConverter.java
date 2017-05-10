package org.samples.blassioli.reddit.features.details.api;

import com.squareup.moshi.FromJson;

import java.util.List;

public class DetailsApiResponseConverter {
    @FromJson
    public DetailsApiResponse fromJson(List<DetailsData> dataContainer) {
        DetailsApiResponse response = new DetailsApiResponse();
        response.childWithT3 = dataContainer.get(0);
        response.childWithT1 = dataContainer.get(1);
        return response;
    }
}