package apps;

import entities.messages.Message;

public interface BasicApp {

    // TODO: change the params
    // TODO: do we also need in User obj to have some methods to receive and send ??
    // TODO:  ^ because every customer needs to send a message/receive ^
    // TODO: or do we delete the methods from BasicApp and have these methods only in User obj ??
    public void receiveMessage(Message message);
    public void sendMessage(Message message);

}
