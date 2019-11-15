package ca.snappay.openapi.response;

import ca.snappay.openapi.constant.Constants;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * This class is the generic type of OpenAPI response.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class OpenApiResponse<T extends OpenApiResponseData> {

    private String code;

    @SerializedName("msg")
    private String message;

    private Long total;

    private List<T> data;

    private String psn;

    private String sign;

    /**
     * Checks if the request is successful.
     *
     * @return true if the request is successful; or false otherwise.
     */
    public boolean isSuccessful() {
        return Constants.CODE_SUCCESS.equals(code);
    }

}
