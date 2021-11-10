package entities.users;

// TODO: a table
public class User {

    private String name;
    private UserType userType;
    private int id;
    private static int count = 0;

    public User(String name, UserType userType) {
        this.name = name;
        this.userType = userType;
        this.id = ++count;
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

    public int getId() {
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
