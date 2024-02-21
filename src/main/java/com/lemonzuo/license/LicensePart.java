package com.lemonzuo.license;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LemonZuo
 * @create 2024-02-20 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicensePart {
    private String licenseId;
    private String licenseeName;
    private String assigneeName;
    private String assigneeEmail;
    private String licenseRestriction;
    private Boolean checkConcurrentUse;
    private List<Product> products;
    private String metadata;
    private String hash;
    private Integer gracePeriodDays;
    private Boolean isAutoProlongated;

    public LicensePart(String licenseId, String licenseeName, String[] codes, String date) {
        this.licenseId = licenseId;
        this.licenseeName = licenseeName;
        this.assigneeName = StrUtil.EMPTY;
        this.products = Arrays.stream(codes).map(code -> new Product(code, date)).collect(Collectors.toList());

        // 一些默认值
        this.checkConcurrentUse = false;
        this.metadata = "0120230914PSAX000005";
        this.hash = "TRIAL:-1920204289";
        this.gracePeriodDays = 7;
        this.isAutoProlongated = true;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Product {
        private String code;
        private String fallbackDate;
        private String paidUpTo;
        private Boolean extended;

        public Product(String code, String date) {
            this.code = code;
            this.fallbackDate = date;
            this.paidUpTo = date;
            this.extended = true;
        }
    }
}
