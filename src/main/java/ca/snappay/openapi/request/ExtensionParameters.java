package ca.snappay.openapi.request;

import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import lombok.Data;

/**
 * The extension parameters supported by certain APIs.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class ExtensionParameters {

    private String storeNo;

    private String subAppId;

    private Integer qrCodeWidth;

    private Integer qrCodeHeight;
    
    public static class ExtensionParametersJsonSerializer
        implements JsonSerializer<ExtensionParameters> {

        @Override
        public JsonElement serialize(ExtensionParameters src, Type typeOfSrc,
                JsonSerializationContext context) {
            StringBuilder sb = new StringBuilder("{");
            if (src.storeNo != null) {
                sb.append("\\\"store_no\\\":\\\"").append(src.storeNo).append("\\\",");
            }
            if (src.subAppId != null) {
                sb.append("\\\"sub_app_id\\\":\\\"").append(src.subAppId).append("\\\",");
            }
            if (src.qrCodeWidth != null) {
                sb.append("\\\"qrcode_pic_size\\\":\\\"").append(src.qrCodeWidth).append(", ").append(src.qrCodeHeight)
                    .append("\\\",");
            }
            if (sb.length() > 1) {
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("}");
            return new JsonPrimitive(sb.toString());
        }

    }
}
