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
package ca.snappay.openapi.request.card;

import ca.snappay.openapi.response.card.QueryCardResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The request for card query.
 *
 * @author shawndu
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryCardRequest extends AbstractCardRequest<QueryCardResponse> {

    private static final String REQUEST_METHOD = "card.query";

    @Override
    public String getRequestMethod() {
        return REQUEST_METHOD;
    }

    public QueryCardRequest(String cardNo) {
        setCardNo(cardNo);
    }

}
