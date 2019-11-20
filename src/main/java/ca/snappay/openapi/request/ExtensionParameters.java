package ca.snappay.openapi.request;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * The extension parameters supported by certain APIs.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class ExtensionParameters {

    @SerializedName("store_no")
    private String storeNo;

}
