package kr.co.uniess.vk.batch.component.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Master {
    @JsonProperty("contentid")
    private String contentId;

    @JsonProperty("contenttypeid")
    private int contentTypeId;

    @JsonProperty("createdtime")
    @JsonDeserialize(using = DateDeserialize.class)
    private long createdDate;

    @JsonProperty("modifiedtime")
    @JsonDeserialize(using = DateDeserialize.class)
    private long modifiedDate;

    @JsonIgnore
    private boolean isWithTour;

    @JsonIgnore
    private boolean isGreenTour;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Master)) return false;
        if (this == o) return true;
        return contentId == ((Master) o).getContentId()
                && contentTypeId == ((Master) o).getContentTypeId();
    }

    public static class DateDeserialize extends JsonDeserializer<Long> {
        private DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {
            ObjectCodec codec = jsonParser.getCodec();
            JsonNode node = codec.readTree(jsonParser);
            return node.asLong();
        }
    }
}
