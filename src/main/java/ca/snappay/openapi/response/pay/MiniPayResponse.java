package ca.snappay.openapi.response.pay;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The response for mini payment.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MiniPayResponse extends OpenApiResponse<MiniPayResponseData> {
}
