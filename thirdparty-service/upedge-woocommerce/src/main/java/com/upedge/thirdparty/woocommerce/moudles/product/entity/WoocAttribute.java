package com.upedge.thirdparty.woocommerce.moudles.product.entity;

import lombok.Data;

import java.util.List;
@Data
public class WoocAttribute {


    /**
     * id : 1
     * name : Color
     * slug : pa_color
     * type : select
     * order_by : menu_order
     * has_archives : true
     * _links : {"self":[{"href":"https://example.com/wp-json/wc/v3/products/attributes/6"}],"collection":[{"href":"https://example.com/wp-json/wc/v3/products/attributes"}]}
     */

    private String id;
    private String name;
    private String slug;
    private String type = "select";
    private String order_by;
    private boolean has_archives;
    private LinksBean _links;


    public static class LinksBean {
        private List<SelfBean> self;
        private List<CollectionBean> collection;

        public List<SelfBean> getSelf() {
            return self;
        }

        public void setSelf(List<SelfBean> self) {
            this.self = self;
        }

        public List<CollectionBean> getCollection() {
            return collection;
        }

        public void setCollection(List<CollectionBean> collection) {
            this.collection = collection;
        }

        public static class SelfBean {
            /**
             * href : https://example.com/wp-json/wc/v3/products/attributes/6
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }

        public static class CollectionBean {
            /**
             * href : https://example.com/wp-json/wc/v3/products/attributes
             */

            private String href;

            public String getHref() {
                return href;
            }

            public void setHref(String href) {
                this.href = href;
            }
        }
    }
}
