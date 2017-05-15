package org.samples.blassioli.reddit.features.details.data;

import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class DepthFirstIteratorTest {

    @Test
    public void testGetPreOrder_shouldReturnNull_ifEmptyString() {
        List<Map<String, Object>> comments = PreOrderCommentsIterator.getPreOrder("");
        assertThat(comments).isNull();
    }

    @Test
    public void testGetPreOrder_shouldReturnNull_ifNull() {
        List<Map<String, Object>> comments = PreOrderCommentsIterator.getPreOrder(null);
        assertThat(comments).isNull();
    }

    @Test
    public void testGetPreOrder_shouldReturnEmptyList_ifInvalidObject() {
        Object invalidObject = new TreeMap<String, Object>();
        List<Map<String, Object>> comments = PreOrderCommentsIterator.getPreOrder(invalidObject);
        assertThat(comments.size()).isZero();
    }

    /*
    {
        kind: "Listing",
        data: {
            children: [
                { kind: "t1", data: { "id": "1" } },
                { kind: "t1", data: { "id": "2" } },
                { kind: "t1", data: { "id": "3" } },
                { kind: "t1", data: { "id": "4" } },
                { kind: "t1", data: { "id": "5" } },
                { kind: "t1", data: { "id": "6" } },
            ]
        }
     */
}
