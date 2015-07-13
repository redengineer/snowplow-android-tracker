/*
 * Copyright (c) 2015 Snowplow Analytics Ltd. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */

package com.snowplowanalytics.snowplow.tracker.events;

import com.snowplowanalytics.snowplow.tracker.constants.Parameters;
import com.snowplowanalytics.snowplow.tracker.constants.TrackerConstants;
import com.snowplowanalytics.snowplow.tracker.utils.Preconditions;
import com.snowplowanalytics.snowplow.tracker.payload.TrackerPayload;

public class EcommerceTransactionItem extends Event {

    private final String itemId;
    private final String sku;
    private final Double price;
    private final Integer quantity;
    private final String name;
    private final String category;
    private final String currency;

    public static abstract class Builder<T extends Builder<T>> extends Event.Builder<T> {

        private String itemId;
        private String sku;
        private Double price;
        private Integer quantity;
        private String name;
        private String category;
        private String currency;

        /**
         * @param itemId Item ID
         */
        public T itemId(String itemId) {
            this.itemId = itemId;
            return self();
        }

        /**
         * @param sku Item SKU
         */
        public T sku(String sku) {
            this.sku = sku;
            return self();
        }

        /**
         * @param price Item price
         */
        public T price(Double price) {
            this.price = price;
            return self();
        }

        /**
         * @param quantity Item quantity
         */
        public T quantity(Integer quantity) {
            this.quantity = quantity;
            return self();
        }

        /**
         * @param name Item name
         */
        public T name(String name) {
            this.name = name;
            return self();
        }

        /**
         * @param category Item category
         */
        public T category(String category) {
            this.category = category;
            return self();
        }

        /**
         * @param currency The currency the price is expressed in
         */
        public T currency(String currency) {
            this.currency = currency;
            return self();
        }

        public EcommerceTransactionItem build() {
            return new EcommerceTransactionItem(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        @Override
        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    protected EcommerceTransactionItem(Builder<?> builder) {
        super(builder);

        // Precondition checks
        Preconditions.checkNotNull(builder.name);
        Preconditions.checkNotNull(builder.category);
        Preconditions.checkNotNull(builder.currency);
        Preconditions.checkArgument(!builder.itemId.isEmpty(), "itemId cannot be empty");
        Preconditions.checkArgument(!builder.sku.isEmpty(), "sku cannot be empty");
        Preconditions.checkArgument(!builder.name.isEmpty(), "name cannot be empty");
        Preconditions.checkArgument(!builder.category.isEmpty(), "category cannot be empty");
        Preconditions.checkArgument(!builder.currency.isEmpty(), "currency cannot be empty");

        this.itemId = builder.itemId;
        this.sku = builder.sku;
        this.price = builder.price;
        this.quantity = builder.quantity;
        this.name = builder.name;
        this.category = builder.category;
        this.currency = builder.currency;
    }

    /**
     * Returns a TrackerPayload which can be stored into
     * the local database.
     *
     * @return the payload to be sent.
     */
    public TrackerPayload getPayload() {
        TrackerPayload payload = new TrackerPayload();
        payload.add(Parameters.EVENT, TrackerConstants.EVENT_ECOMM_ITEM);
        payload.add(Parameters.TI_ITEM_ID, this.itemId);
        payload.add(Parameters.TI_ITEM_SKU, this.sku);
        payload.add(Parameters.TI_ITEM_NAME, this.name);
        payload.add(Parameters.TI_ITEM_CATEGORY, this.category);
        payload.add(Parameters.TI_ITEM_PRICE, Double.toString(this.price));
        payload.add(Parameters.TI_ITEM_QUANTITY, Integer.toString(this.quantity));
        payload.add(Parameters.TI_ITEM_CURRENCY, this.currency);
        return payload;
    }
}
