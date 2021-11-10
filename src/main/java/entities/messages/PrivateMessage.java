package entities.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrivateMessage {

    private Long timestamp;
    private String destination;
    private PrivateMessageType typePrivate;
    private String payload;

    public PrivateMessage(String payload, Long timestamp, String destination, PrivateMessageType typePrivate) {
        this.payload = payload;
        this.timestamp = timestamp;
        this.destination = destination;
        this.typePrivate = typePrivate;
    }
}
