package org.samples.blassioli.reddit.features.details.data;

import com.google.common.base.Optional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class PreOrderCommentsIterator {
    private static final List<Map<String, Object>> EMPTY_LIST = new ArrayList<>();

    private static List<Map<String, Object>> dfs(Map<String, Object> root) {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String, Object> cur;
        Stack<Map<String, Object>> stk = new Stack<>();
        stk.push(root);
        while (!stk.empty()) {
            cur = stk.pop();
            for (Map<String, Object> child : getChildren(cur)) {
                result.add(child);
                stk.push(child);
            }
        }
        return result;
    }

    private static List<Map<String, Object>> getChildren(Map<String, Object> node) {
        if (node == null) {
            return EMPTY_LIST;
        }

        Map<String, Object> data = asHashMapOrNull(node.get("data"));
        if (data != null) {
            // replies may be empty String
            Map<String, Object> replies = asHashMapOrNull(data.get("replies"));
            if (replies == null) {
                // Same as node.get("data").get("children")
                return asList(data.get("children"));
            } else {
                // Same as node.get("data").get("replies")
                return getChildren(replies);
            }
        }
        return EMPTY_LIST;
    }

    private static Map<String, Object> asHashMapOrNull(Object obj) {
        try {
            return (Map<String, Object>) obj;
        } catch (Exception e) {
            return null;
        }
    }

    private static List<Map<String, Object>> asList(Object obj) {
        if (obj == null) {
            return EMPTY_LIST;
        }
        return (List<Map<String, Object>>) obj;
    }

    public static List<Map<String, Object>> getPreOrder(Object obj) {
        return Optional.fromNullable(asHashMapOrNull(obj))
                .transform(node -> dfs(node))
                .orNull();
    }
}
