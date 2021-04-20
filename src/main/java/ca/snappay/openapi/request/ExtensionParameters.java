/*
 * Copyright 2021 SnapPay Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.snappay.openapi.request;

import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
            JsonObject jsonObject = new JsonObject();
            if (src.storeNo != null) {
                jsonObject.addProperty("store_no", src.storeNo);
            }
            if (src.subAppId != null) {
                jsonObject.addProperty("sub_app_id", src.subAppId);
            }
            if (src.qrCodeWidth != null) {
                jsonObject.addProperty("qrcode_pic_size", src.qrCodeWidth + "," + src.qrCodeHeight);
            }
            return new JsonPrimitive(jsonObject.toString());
        }

    }
}
