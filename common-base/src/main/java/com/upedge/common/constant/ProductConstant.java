package com.upedge.common.constant;

public class ProductConstant {

    public static final Integer DEFAULT_WAREHOURSE_ID = 3;

    //商品状态（0:选品池 1:编辑中2:下架3: 上架 4:机器上架 ）
    public enum State{
        ABANDONPOOL(-1,"废弃池"),
        CHOOSING(0,"选品池"),
        EDITING(1,"收藏夹"),
        UNSHELVE(2,"下架"),
        PUTAWAY(3,"上架"),
        AUTOPUTAWAY(4,"机器上架"),
        GROUPPUTAWAY(5,"捆绑上架");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        State(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    //速卖通替换状态 0未替换 1替换
    public enum ReplaceState {
        YES(0,"未替换"),
        NO(1,"替换");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ReplaceState(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    //赛盒上传状态 0:未上传 1:已上传
    public enum SaiheState {
        YES(1,"已上传"),
        NO(0,"未上传");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        SaiheState(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    //0:1688 1:个人添加 2:复制产品3:捆绑产品
    public enum ProductSource {
        AlI1688(0,"1688"),
        GROUPPRODUCT(3,"捆绑产品");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductSource(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    //0:公有产品 1:私有产品
    public enum ProductType {
        PUBLIC(0,"公有产品"),
        PRIVATE(1,"私有产品");

        private int code;

        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProductType(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

    }

    //产品属性Key
    public enum ProductAttrKeys {
        receipt_remark("receiptRemark","采购收货备注"),
        procurement_check_memo("procurementCheckMemo","质检备注"),
        delivery_pack_note("deliveryPackNote","发货打包备注");

        private String attrKey;

        private String msg;

        public String getAttrKey() {
            return attrKey;
        }

        public String getMsg() {
            return msg;
        }

        ProductAttrKeys(String attrKey, String msg) {
            this.attrKey = attrKey;
            this.msg = msg;
        }

    }
}
