package lana.post;

import lombok.Data;

@Data
public class PostPayload {
    private String text;
    private Byte[] binaryData;
    private String fileId;
}
