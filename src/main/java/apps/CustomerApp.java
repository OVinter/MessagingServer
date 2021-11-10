package apps;

import entities.messages.PrivateMessage;
import entities.users.User;
import entities.users.UserType;

import java.util.ArrayList;

public class CustomerApp {

    private ArrayList<User> user;

    public CustomerApp(String name) {
        this.user = new ArrayList<>(3);
    }

    public void receiveMessage() {
        // TODO
    }

    public void sendMessage(PrivateMessage message) {
        // TODO
    }

    public void addUser(String name) {
        this.user.add(new User(name, UserType.CUSTOMER));
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public static void main(String[] args) {
        System.out.println("Customer App");
    }
}
