package fudan.se.lab5.entity;

import java.text.MessageFormat;

public class User {
    public static int count = 1;
    private String name;
    private String password;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MessageFormat.format("Name: {0}, Password: {1}, ID:{}", this.getName(), this.getPassword(), this.getId());
    }
}
