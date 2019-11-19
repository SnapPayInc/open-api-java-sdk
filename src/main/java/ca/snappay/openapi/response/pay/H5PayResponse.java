package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;

/**
 * The response for H5 payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class H5PayResponse extends OpenApiResponse<H5PayResponseData> {

    @Override
    public String toString() {
        return super.toString();
    }

}
