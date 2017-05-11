package org.samples.blassioli.reddit.features.details.api;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DetailsApiResponseConverterTest {

    public List<DetailsData> fakeData;
    private DetailsApiResponseConverter converter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        converter = new DetailsApiResponseConverter();
        fakeData = makeRandomFakeData();
    }

    @Test
    public void testExtractT3() {
        DetailsApiResponse response = converter.fromJson(fakeData);

        assertThat(response.childWithT3)
                .isEqualTo(fakeData.get(0));
    }

    @Test
    public void testExtractT1() {
        DetailsApiResponse response = converter.fromJson(fakeData);

        assertThat(response.childWithT1)
                .isEqualTo(fakeData.get(1));
    }

    private List<DetailsData> makeRandomFakeData() {
        List<DetailsData> list = new ArrayList<>();
        DetailsData t3Child = new DetailsData();
        list.add(t3Child);
        DetailsData t1Child = new DetailsData();
        list.add(t1Child);
        return list;
    }
}
