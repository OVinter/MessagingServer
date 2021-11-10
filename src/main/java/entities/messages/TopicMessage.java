package entities.messages;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TopicMessage {

    private TopicMessageType typeTopic;
    private Long timestamp;
    private String payload;

    public TopicMessage(String payload, TopicMessageType typeTopic, Long timestamp) {
        this.payload = payload;
        this.typeTopic = typeTopic;
        this.timestamp = timestamp;
    }

}
