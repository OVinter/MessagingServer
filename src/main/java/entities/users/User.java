package entities.users;

import java.util.UUID;

// TODO: a table
public class User {

    private String name;
    private UserType userType;
    private UUID id;

    public User(String name, UserType userType) {
        this.name = name;
        this.userType = userType;
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", userType=" + userType +
                ", id=" + id +
                '}';
    }
}
