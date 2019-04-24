package com.ciaj.comm.constant;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/20 15:54
 * @Description:
 */
public class DefaultConfigConstant {
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    /**
     * 云服务商
     */
    public enum OSSCloud {

        /**
         * 阿里云
         */
        ALIYUN("ALY", "阿里云"),
        /**
         * 七牛云
         */
        QINIUCLOUD("QNY", "七牛云"),
        /**
         * 腾讯云
         */
        QCLOUD("TXY", "腾讯云");

        private String value;
        private String name;

        OSSCloud(String value, String name) {
            this.value = value;
            this.name = name;
        }

        public String getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static OSSCloud getEnum(String key) {
            for (OSSCloud cloudService : OSSCloud.values()) {
                if (cloudService.getValue().equalsIgnoreCase(key) ||  cloudService.getName().equalsIgnoreCase(key)) {
                    return cloudService;
                }
            }
            return null;
        }
    }
}
