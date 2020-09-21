import com.google.gson.JsonElement;

public class Model {
    private int id;
    private String type;
    private String sourceDataUrl;
    private String tokenDataUrl;
    private String videoUrl;
    private String value;
    private int ttl;

    public void setValue(String value) {
        this.value = value;
    }

    public Model(int id, String type, String sourceDataUrl, String tokenDataUrl, String videoUrl, String value, int ttl) {
        this.id = id;
        this.type = type;
        this.sourceDataUrl = sourceDataUrl;
        this.tokenDataUrl = tokenDataUrl;
        this.videoUrl = videoUrl;
        this.value = value;
        this.ttl = ttl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSourceDataUrl() {
        return sourceDataUrl;
    }

    public void setSourceDataUrl(String sourceDataUrl) {
        this.sourceDataUrl = sourceDataUrl;
    }

    public String getTokenDataUrl() {
        return tokenDataUrl;
    }

    public void setTokenDataUrl(String tokenDataUrl) {
        this.tokenDataUrl = tokenDataUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getValue() {
        return value;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    @Override
    public String toString() {
        return "ID=" + getId()
                + " TTL=" +getTtl()
                + " type=" + getType()
                + " sourceDataUrl=" + getSourceDataUrl()
                + " getTokenDataUrl=" + getTokenDataUrl()
                + " videoUrl=" + getVideoUrl();

    }
}
