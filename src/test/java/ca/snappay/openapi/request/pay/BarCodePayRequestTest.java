package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.request.OpenApiRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

public class BarCodePayRequestTest {

    @Test
    public void testJson() {
        BarCodePayRequest request = new BarCodePayRequest();
        request.setDescription("desc");


    }
}
