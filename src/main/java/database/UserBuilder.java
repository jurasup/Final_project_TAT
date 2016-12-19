package database;

/**
 * Provides creation of users for testing.
 * @author Yury Suponev
 */
public class UserBuilder {
    private long nextID = 100;

    public static final String ADMIN = "administrator";
    public static final String EDITOR = "editor";
    public static final String AUTHOR = "author";
    public static final String CONTRIBUTOR = "contributor";
    public static final String SUBSCRIBER = "subscriber";

    /**
     * Provides creation of user with defined status.
     * @param status
     * @return User instance
     */
    public User build(String status) {
        switch (status) {
            case ADMIN:
                return newUser(status, UserMeta.ADMIN_LEVEL);
            case EDITOR:
                return newUser(status, UserMeta.EDITOR_LEVEL);
            case AUTHOR:
                return newUser(status, UserMeta.AUTHOR_LEVEL);
            case CONTRIBUTOR:
                return newUser(status, UserMeta.CONTRIBUTOR_LEVEL);
            case SUBSCRIBER:
                return newUser(status, UserMeta.SUBSCRIBER_LEVEL);
            default:
                return newUser(status, UserMeta.SUBSCRIBER_LEVEL);
        }
    }

    private User newUser(String status, int level) {
        User user = new User(nextID, status + nextID,
                status, status + nextID + "@gmail.com", new UserMeta(status, level));
        nextID++;
        return user;
    }
}
