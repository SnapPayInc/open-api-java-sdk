package ca.snappay.openapi.request.pay;

import ca.snappay.openapi.response.pay.UnitedPayResponse;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class UnitedPayRequest extends AbstractPayRequest<UnitedPayResponse> {

  private static final String REQUEST_METHOD = "pay.unitedpay";

  @SerializedName("return_url")
  private String returnUrl;

  @Override
  public String getRequestMethod() {
    return REQUEST_METHOD;
  }

  @Override
  public void validate() {
    super.validate();
    validateLength("returnUrl", returnUrl, 256);
    validateRequired("effectiveMinutes", this.getEffectiveMinutes());
    validateRequired("returnUrl", returnUrl);
  }
}
