package com.mooc.mall.form;


import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author gaomy
 * @Date 2022/2/22 14:29
 * @Description
 * @Version 1.0
 */

@Data
public class ShippingForm {

    @NotBlank
    private String receiverName;

    @NotBlank
    private String receiverPhone;

    @NotBlank
    private String receiverMobile;

    @NotBlank
    private String receiverProvince;

    @NotBlank
    private String receiverCity;

    @NotBlank
    private String receiverDistrict;

    @NotBlank
    private String receiverAddress;

    @NotBlank
    private String receiverZip;

}
