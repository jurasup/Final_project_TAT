package database;

import java.util.HashMap;

/**
 * Represents an user entity. There are 5 types of possible users: administrator, editor,
 * author, contributor, subscriber.
 * @author Yury Suponev
 */
public class User {
    private long ID;
    private String login;
    private String password;
    private String email;
    private String creationTime = "2016-12-05 00:00:00";
    private UserMeta meta;

    public User(long ID, String login, String password, String email, UserMeta meta) {
        this.ID = ID;
        this.login = login;
        this.password = password;
        this.email = email;
        this.meta = meta;
    }

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public HashMap<String, String> getMeta() {
        return this.meta.getData();
    }

    public void setMeta(UserMeta meta) {
        this.meta = meta;
    }
}
