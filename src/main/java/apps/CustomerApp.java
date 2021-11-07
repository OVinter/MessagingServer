package apps;

import entities.messages.Message;
import entities.users.User;
import entities.users.UserType;

import java.util.ArrayList;

public class CustomerApp implements BasicApp{

    private ArrayList<User> user;

    public CustomerApp(String name) {
        this.user = new ArrayList<>(3);
    }

    @Override
    public void receiveMessage(Message message) {

    }

    @Override
    public void sendMessage(Message message) {

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
}
