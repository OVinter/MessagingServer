package entities.messages;

import java.util.Date;
import java.util.Objects;

public class TopicMessage extends Message{

    private TopicMessageType type;
    private Date timestamp;

    public TopicMessage(String payload, TopicMessageType type, Date timestamp) {
        super(payload);
        this.type = type;
        this.timestamp = timestamp;
    }

    public TopicMessageType getType() {
        return type;
    }

    public void setType(TopicMessageType type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TopicMessage{" +
                "type=" + type +
                ", timestamp=" + timestamp +
                "} " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TopicMessage that = (TopicMessage) o;
        return type == that.type && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, timestamp);
    }

    public void getDatee(){}
}
