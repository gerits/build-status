package be.rubengerits.buildstatus.model.data;

import android.content.ContentValues;

public class Account {

    private Integer id;
    private String username;
    private String token;
    private AccountType type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public ContentValues toDatabase() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("username", username);
        contentValues.put("token", token);
        contentValues.put("type", type.getId());
        return contentValues;
    }
}
