package com.kevinchamorro.bancopichincha.util;

import java.math.BigDecimal;

public class TransactionUtil {
    public static String transactionTypeByTransactionValue(BigDecimal transactionValue){
        if (transactionValue.compareTo(BigDecimal.ZERO) > 0){
            return "DEPOSITO";
        } else {
            return "RETIRO";
        }
    }
}
