package ca.snappay.openapi.request;

import ca.snappay.openapi.response.OpenApiResponse;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;

/**
 * This class is the generic type of OpenAPI request.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
public abstract class OpenApiRequest<T extends OpenApiResponse> {

    /**
     * The timestamp. If provided, it has to be in UTC timezone.
     */
    private LocalDateTime timestamp;

    /**
     * Gets the corresponding response type.
     *
     * @return the response type.
     */
    public Class<T> getResponseClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * Gets the request method.
     *
     * @return the request method.
     */
    public abstract String getRequestMethod();

    /**
     * Determines if the request needs to send merchant number.
     *
     * @return true if the request needs to send merchant number; or false otherwise.
     */
    public boolean needMerchant() {
        return true;
    }

    /**
     * Validates if the request is valid.
     */
    public abstract void validate();

    protected void validateRequired(String fieldName, Object field) {
        if (field == null) {
            throw new IllegalArgumentException("Missing required field " + fieldName);
        }
    }

    protected void validateRequired(String fieldName, String field) {
        if (StringUtils.isEmpty(field)) {
            throw new IllegalArgumentException("Missing required field " + fieldName);
        }
    }

    protected void validateLength(String fieldName, String field, int maxLength) {
        if (field == null) {
            return;
        }
        if (field.trim().length() > maxLength) {
            throw new IllegalArgumentException("Field " + fieldName + " is too long, max length " + maxLength);
        }
    }

    protected void validateRange(String fieldName, int field, int min, int max) {
        if (field < min) {
            throw new IllegalArgumentException("Field " + fieldName + " is too small, min value " + min);
        }
        if (field > max) {
            throw new IllegalArgumentException("Field " + fieldName + " is too big, max value " + max);
        }
    }

    protected void validateRange(String fieldName, double field, double min, double max) {
        if (field < min) {
            throw new IllegalArgumentException("Field " + fieldName + " is too small, min value " + min);
        }
        if (field > max) {
            throw new IllegalArgumentException("Field " + fieldName + " is too big, max value " + max);
        }
    }

}
