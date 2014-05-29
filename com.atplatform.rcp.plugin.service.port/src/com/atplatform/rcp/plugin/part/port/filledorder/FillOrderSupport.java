package com.atplatform.rcp.plugin.part.port.filledorder;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import com.atplatform.rcp.datacenter.beans.FilledOrder;

public class FillOrderSupport {
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getTradeDateTime(FilledOrder order) {
        if (order.getTradeDateTime() == null) return "";

        return order.getTradeDateTime();
    }

    public static String getClientAccount(FilledOrder order) {
        String str = order.getClientAccountNumber();
        if(str == null)
            return "";
        return str;
    }

    public static String getClientName(FilledOrder order) {
        String str = order.getClientAccountName();
        if(str == null)
            return "";
        return str;
    }

    public static String getExchange(FilledOrder order) {
        String str = order.getExchangeShortName();
        if(str == null)
            return "";
        return str;
    }

    public static String getProductCode(FilledOrder order) {
        String str = order.getProductCode();
        if(str == null)
            return "";
        return str;
    }

    public static String getProductName(FilledOrder order) {
        String str = order.getProductName();
        if(str == null)
            return "";
        return str;
    }

    public static String getProductType(FilledOrder order) {
        String str = order.getProductTypeString();
        if(str == null)
            return "";
        return str;
    }

    public static String getContractMonthDate(FilledOrder order) {
        Integer productType = order.getProductType();
        if (productType == null) return "";

        if (productType == 1)
            return order.getFutureContractMonthDay();
        else
            return order.getOptionContractMonthDay();
    }

    public static String getPutCallOption(FilledOrder order) {
        Integer o = order.getOptionRightCode();
        if (o == null) return "";
        if (o == 1)
            return "P";
        else if(o == 2)
            return "C";
        return "";
    }

    public static String getStrikePrice(FilledOrder order) {
        BigDecimal str = order.getStrikePrice();
        if (str == null) return "";
        if (str.compareTo(BigDecimal.ZERO) == 0) return "";
        return str.toPlainString();
    }

    public static String getTradeType(FilledOrder order) {
        String str = order.getTradeTypeString();
        if(str == null)
            return "";
        return str;
    }

    public static String getBusinessType(FilledOrder order) {
        if (order.getBusinessType() == null) return "";

        Integer type = order.getBusinessType();
        if (type == null) return "";

        if (type == 1) return "E";
        if (type == 2) return "C";
        if (type == 3) return "EC";
        if (type == 4) return "O";

        return "";
    }

    public static String getBuySell(FilledOrder order) {
        if (order.getSide() == null) return "";

        return order.getSide() == 1 ? "B" : "S";
    }

    public static String getQuantity(FilledOrder order) {
        if (order.getLots() == null) return "";

        return order.getLots().toPlainString();
    }

    public static String getPrice(FilledOrder order) {
        if (order.getClientPrice() == null) return "";

        return order.getClientPrice().toPlainString();
    }

    public static String getIntermediateAccount(FilledOrder order) {
        String str = order.getIntermediaAccountNumber();
        if(str == null)
            return "";
        return str;
    }

    public static String getBrokerPrice(FilledOrder order) {
        if (order.getBrokerPrice() == null) return "";

        return order.getBrokerPrice().toPlainString();
    }

    public static String getExecutionBrokerAccount(FilledOrder order) {
        String str = order.getExecuteBrokerAccountNumber();
        if(str == null)
            return "";
        return str;
    }

    public static String getExecutionBrokerName(FilledOrder order) {
        String str = order.getExecuteBrokerAccountName();
        if(str == null)
            return "";
        return str;
    }

    public static String getClearingBrokerAccount(FilledOrder order) {
        String str = order.getClearingBrokerAccountNumber();
        if(str == null)
            return "";
        return str;
    }

    public static String getClearingBrokerName(FilledOrder order) {
        String str = order.getClearingBrokerAccountName();
        if(str == null)
            return "";
        return str;
    }

    public static String getIntroducingBrokerAccount(FilledOrder order) {
        String str = order.getIntroducingBrokerAccountNumber();
        if(str == null)
            return "";
        return str;
    }

    public static String getIntroducingBrokerName(FilledOrder order) {
        String str = order.getIntroducingBrokerAccountName();
        if(str == null)
            return "";
        return str;
    }

    // ///////// setters

    public static void setProductType(FilledOrder order, String val) throws Exception {
        order.setProductTypeString(val);
        if (val.equalsIgnoreCase("FUT"))
            order.setProductType(1);
        else if (val.equalsIgnoreCase("OOF"))
            order.setProductType(2);
        else if (val.equalsIgnoreCase("TAPO"))
            order.setProductType(3);
        else
            throw new Exception();
    }

    public static void setPutCallOption(FilledOrder order, String val) throws Exception{
        if (val.equalsIgnoreCase("P")) order.setOptionRightCode(1);
        else if (val.equalsIgnoreCase("C")) order.setOptionRightCode(2);
        else if (order.getProductType().intValue() == 2) throw new Exception(); 
    }

    public static void setBuySell(FilledOrder order, String val) throws Exception {
        if (val.compareToIgnoreCase("B") == 0)
            order.setSide(1);
        else if (val.compareToIgnoreCase("S") == 0)
            order.setSide(2);
        else
            throw new Exception();
    }

    public static void setBusinessType(FilledOrder order, String val) throws Exception {
        if (val.equalsIgnoreCase("E"))
            order.setBusinessType(1);
        else if (val.equalsIgnoreCase("C"))
            order.setBusinessType(2);
        else if (val.equalsIgnoreCase("EC"))
            order.setBusinessType(3);
        else if (val.equalsIgnoreCase("O"))
            order.setBusinessType(4);
        else
            throw new Exception();
    }

    public static void setTradeType(FilledOrder order, String val) throws Exception {
        order.setTradeTypeString(val);
        if (val.equalsIgnoreCase("normal"))
            order.setTradeType(1);
        else if (val.equalsIgnoreCase("option assign"))
            order.setTradeType(2);
        else if (val.equalsIgnoreCase("option exercise"))
            order.setTradeType(3);
        else if (val.equalsIgnoreCase("option abandon"))
            order.setTradeType(4);
        else
            throw new Exception();
    }

}
