package entities.messages;

import java.util.Objects;

public abstract  class Message {

    protected String payload;

    public Message(String payload) {
        this.payload = payload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(payload, message.payload);
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Message{" +
                "payload='" + payload + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload);
    }
}
