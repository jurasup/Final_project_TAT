package database;

import java.util.HashMap;

/**
 * Provides storage for user's metadata.
 * @author Yury Suponev
 */
public class UserMeta {
    private static final String capability = "wp_capabilities";
    private static final String userLevel = "wp_user_level";
    public static final int ADMIN_LEVEL = 10;
    public static final int EDITOR_LEVEL = 7;
    public static final int AUTHOR_LEVEL = 2;
    public static final int CONTRIBUTOR_LEVEL = 1;
    public static final int SUBSCRIBER_LEVEL = 0;

    private HashMap<String, String> data;

    public UserMeta(String status, int level) {
        data = new HashMap<>();
        data.put(capability, capabilityKey(status));
        data.put(userLevel, String.valueOf(level));
    }

    private String capabilityKey(String status) {
        StringBuilder builder = new StringBuilder("a:1:{s:");
        builder.append(status.length())
                .append(":\"")
                .append(status)
                .append("\";b:1;}");
        return builder.toString();
    }

    public HashMap<String, String> getData() {
        return this.data;
    }
}
