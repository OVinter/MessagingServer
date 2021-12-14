package appsUtilities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class PayloadObject {

    private String payload;
    private UUID userId;
    private boolean isResponse;
}
