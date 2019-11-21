package ca.snappay.openapi.response.misc;

import ca.snappay.openapi.response.OpenApiResponseData;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * The response data for exchange rate query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public class QueryExchangeRateResponseData implements OpenApiResponseData {

    @SerializedName("exchange_rate")
    private String exchangeRate;

}
