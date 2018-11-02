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
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Master {
    @JsonProperty("modifiedtime")
    @JsonDeserialize(using = DateDeserialize.class)
    private long modifiedDate;
    @JsonProperty("createdtime")
    @JsonDeserialize(using = DateDeserialize.class)
    private long createdDate;
    @JsonProperty("contentid")
    private String contentId;
    @JsonProperty("contenttypeid")
    private int contentTypeId;
    private String title;
    private String firstimage;
    private String firstimage2;
    private String addr1;
    private String addr2;
    private String cat1;
    private String cat2;
    private String cat3;
    private String areacode;
    private String sigungucode;
    private String zipcode;
    private String tel;
    private int mlevel;
    private double mapx;
    private double mapy;
    private int readcount;

    private boolean withTour;
    private boolean greenTour;
    private String mainimage;
    private String booktour;
    private String summary;
    private String telname;

    @JsonIgnore
    private List<String> departments;
    @JsonIgnore
    private List<String> tags;

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
