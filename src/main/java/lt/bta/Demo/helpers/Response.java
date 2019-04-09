package lt.bta.Demo.helpers;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
   private String error;
   private int errorCode;
   private Object data;

    public Response(String error, int errorCode) {
        this.error = error;
        this.errorCode = errorCode;
    }

    public Response(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getData() {
        return data;
    }
}
