package com.fsd07.springbootlibrary.requestmodels;

import lombok.Data;

/**
 * @Author: Yeming Hu
 * @Date: 9/12/2023
 * @Description: com.fsd07.springbootlibrary.requestmodels
 * @Version: 1.0
 */

@Data
public class PaymentInfoRequest {
    private int amount;
    private String currency;
    private String receiptEmail;
}
