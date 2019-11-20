package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;

/**
 * The response for website payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class WebsitePayResponse extends OpenApiResponse<WebsitePayResponseData> {

    @Override
    public String toString() {
        return super.toString();
    }

}
