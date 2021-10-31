package entities.messages;

import java.util.Date;

public class PrivateMessage extends Message {

    private Date timestamp;
    private String destination;
    private PrivateMessageType type;

    public PrivateMessage(String payload, Date timestamp, String destination, PrivateMessageType type) {
        super(payload);
        this.timestamp = timestamp;
        this.destination = destination;
        this.type = type;
    }


}
