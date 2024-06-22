package web.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"cause", "stackTrace", "suppressed", "localizedMessage"})
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 105L;

    private String message;
    private String description;
    private String path;
    private int httpCode;

    public CommonException() {
        this("An error occured");
    }
    public CommonException(String message) {
        this(message, "That's all we know. ");
    }
    public CommonException(String message, String description) {
        this(message, description, "error/unknown");
    }
    public CommonException(String message, String description, String path) {
        this(message, description, path, 500);
    }
    public CommonException(String message, String description, String path, int httpCode) {
        super(message);
        this.setMessage(message);
        this.setDescription(description);
        this.setPath(path);
        this.setHttpCode(httpCode);
    }
    @JsonIgnore
    public int getHttpCode() {
        return httpCode;
    }
    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty("path")
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    @JsonIgnore
    public String toString() {
        return String.format(
            "[%s] %s\n%s", this.getPath(), this.getMessage(), this.getDescription()
        );
    }

}
