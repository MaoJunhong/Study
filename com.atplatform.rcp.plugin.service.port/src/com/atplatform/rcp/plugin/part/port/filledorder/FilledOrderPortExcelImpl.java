package com.atplatform.rcp.plugin.part.port.filledorder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.atplatform.rcp.datacenter.beans.FilledOrder;
import com.atplatform.rcp.plugin.part.port.POIUtils;

public class FilledOrderPortExcelImpl {
    private static String[] FILL_ORDER_HEADER = new String[] { "Trade Datetime", "Client Account", "Client Name", "Exchange", "Product Code", "Product Name",
            "Product Type", "Contract Month/Date", "Put/Call Option", "Strike Price", "Trade Type", "Business Type", "Buy/Sell", "Quantity", "Price",
            "Intermediate Account", "Broker Price", "Execution Broker Account", "Execution Broker Name", "Clearing Broker Account", "Clearing Broker Name",
            "Introducing Broker Account", "Introducing Broker Name" };
    private static String[] FILL_ORDER_REPORT_HEADER = new String[] { "Trade Datetime", "Client Account", "Client Name", "Exchange", "Product Code",
            "Product Name", "Product Type", "Contract Month/Date", "Put/Call Option", "Strike Price", "Trade Type", "Business Type", "Buy/Sell", "Quantity",
            "Price", "Intermediate Account", "Broker Price", "Execution Broker Account", "Execution Broker Name", "Clearing Broker Account",
            "Clearing Broker Name", "Introducing Broker Account", "Introducing Broker Name", "Original Row Number", "Import Status" };
    public static int formatErrorCnt = 0;
    public static List<Row> formatErrorRow = new ArrayList<>();
    public static List<FilledOrder> idErrorOrder = new ArrayList<>();
    public static List<FilledOrder> insertErrorOrder = new ArrayList<>();
    public static List<FilledOrder> feeErrorOrder = new ArrayList<>();
    public static List<FilledOrder> allSuccessOrder = new ArrayList<>();

    public static void exportFillOrder(List<FilledOrder> list, String fileName, boolean isE2007) throws Exception {
        Workbook wb = null;
        if (isE2007) {
            wb = new XSSFWorkbook(); // 2007
        } else {
            wb = new HSSFWorkbook(); // 2003
        }
        Sheet sheet = wb.createSheet();

        // export head
        Row head = sheet.createRow(0);
        for (int colIdx = 0; colIdx < FILL_ORDER_HEADER.length; ++colIdx) {
            Cell cell = head.createCell(colIdx);
            cell.setCellValue(FILL_ORDER_HEADER[colIdx]);
        }

        // export content
        for (int rowIdx = 0; rowIdx < list.size(); ++rowIdx) {
            FilledOrder order = list.get(rowIdx);
            Row row = sheet.createRow(rowIdx + 1);
            saveFillOrder(order, row, false);
        }

        for (int colIdx = 0; colIdx < FILL_ORDER_HEADER.length; ++colIdx) {
            sheet.autoSizeColumn(colIdx);
        }

        if (isE2007) {
            fileName += ".xlsx";
        } else {
            fileName += ".xls";
        }
        wb.write(new FileOutputStream(fileName));
    }

    public static void saveImportResult(String fileName, String res, boolean isE2007) throws Exception {
        Workbook wb = null;
        if (isE2007) {
            wb = new XSSFWorkbook(); // 2007
        } else {
            wb = new HSSFWorkbook(); // 2003
        }
        Sheet sheet = wb.createSheet();
        int rowIdex = 0;

        // save report
        Row row = sheet.createRow(rowIdex++);
        Cell cell = row.createCell(0);
        cell.setCellValue(res);

        // export head
        row = sheet.createRow(rowIdex++);
        for (int colIdx = 0; colIdx < FILL_ORDER_REPORT_HEADER.length; ++colIdx) {
            cell = row.createCell(colIdx);
            cell.setCellValue(FILL_ORDER_REPORT_HEADER[colIdx]);
        }

        // save formatErrorOrder
        for (Row srow : formatErrorRow) {
            row = sheet.createRow(rowIdex++);
            POIUtils.copyRow(wb, srow, row, true);
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue(srow.getRowNum());
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue("Format Error");
        }

        // save idErrorOrder
        for (FilledOrder order : idErrorOrder) {
            row = sheet.createRow(rowIdex++);
            saveFillOrder(order, row, true);
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue("ID Error");
        }

        // save insertErrorOrder
        for (FilledOrder order : insertErrorOrder) {
            row = sheet.createRow(rowIdex++);
            saveFillOrder(order, row, true);
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue("Insert Error");
        }

        // save feeErrorOrder
        for (FilledOrder order : feeErrorOrder) {
            row = sheet.createRow(rowIdex++);
            saveFillOrder(order, row, true);
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue("Fee Error");
        }

        // save SuccessOrder
        for (FilledOrder order : allSuccessOrder) {
            row = sheet.createRow(rowIdex++);
            saveFillOrder(order, row, true);
            cell = row.createCell(row.getLastCellNum());
            cell.setCellValue("Success");
        }

        for (int colIdx = 0; colIdx < FILL_ORDER_REPORT_HEADER.length; ++colIdx) {
            sheet.autoSizeColumn(colIdx);
        }
        if (isE2007) {
            fileName += ".xlsx";
        } else {
            fileName += ".xls";
        }
        wb.write(new FileOutputStream(fileName));
    }

    public static List<FilledOrder> importFillOrder(String fileName, boolean withHead) throws Exception {

        formatErrorRow.clear();
        idErrorOrder.clear();
        insertErrorOrder.clear();
        feeErrorOrder.clear();
        allSuccessOrder.clear();

        boolean isE2007 = false;
        if (fileName.endsWith("xlsx")) isE2007 = true;
        InputStream input = new FileInputStream(fileName);
        Workbook wb = null;

        if (isE2007)
            wb = new XSSFWorkbook(input);
        else
            wb = new HSSFWorkbook(input);
        Sheet sheet = wb.getSheetAt(0);

        List<FilledOrder> list = new ArrayList<>();
        Iterator<Row> rows = sheet.rowIterator();
        if (withHead) rows.next();
        while (rows.hasNext()) {
            Row row = rows.next();
            // System.out.println("Row #" + row.getRowNum());
            FilledOrder order = getFilledOrder(row, wb);

            if (order != null) {
                list.add(order);
                order.setLastUpdateTime(row.getRowNum());
            } else
                formatErrorRow.add(row);
        }
        formatErrorCnt = formatErrorRow.size();
        // System.err.println("error format: " +
        // FilledOrderPortExcelImpl.formatErrorRow.size());
        return list;
    }

    private static FilledOrder getFilledOrder(Row row, Workbook wb) {
        FilledOrder order = new FilledOrder();
        Iterator<Cell> cells = row.cellIterator();

        boolean first = true;
        Cell cell = cells.next();
        if (getCellValue(cell).equalsIgnoreCase("")) return null;

        boolean formatError = false;
        while (cells.hasNext()) {
            if (!first) cell = cells.next();
            first = false;
            // System.out.println("Cell #" + cell.getColumnIndex() + ", cell=" +
            // cell.toString());

            String val = getCellValue(cell);
            if (val == null) val = "";

            switch (cell.getColumnIndex()) {
            case 0:
                order.setTradeDateTime(val);
                break;
            case 1:
                val = toNormalString(val);
                order.setClientAccountNumber(val);
                break;
            case 2:
                order.setClientAccountName(val);
                break;
            case 3:
                order.setExchangeShortName(val);
                break;
            case 4:
                order.setProductCode(val);
                break;
            case 5:
                order.setProductName(val);
                break;
            case 6:
                try {
                    FillOrderSupport.setProductType(order, val);
                } catch (Exception e1) {
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 7:
                val = toNormalString(val);
                if (order.getProductType() == 1)
                    order.setFutureContractMonthDay(val);
                else
                    order.setOptionContractMonthDay(val);
                break;
            case 8:
                try {
                    FillOrderSupport.setPutCallOption(order, val);
                } catch (Exception e2) {
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 9:
                if (val != null && !val.equalsIgnoreCase("")) {
                    try {
                        order.setStrikePrice(new BigDecimal(val));
                    } catch (Exception e) {
                        // e.printStackTrace();
                        setCellStyle(cell, wb);
                        formatError = true;
                    }
                }
                break;
            case 10:
                try {
                    FillOrderSupport.setTradeType(order, val);
                } catch (Exception e1) {
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 11:
                try {
                    FillOrderSupport.setBusinessType(order, val);
                } catch (Exception e1) {
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 12:
                try {
                    FillOrderSupport.setBuySell(order, val);
                } catch (Exception e1) {
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 13:
                try {
                    order.setLots(new BigDecimal(val));
                } catch (Exception e) {
                    // e.printStackTrace();
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 14:
                try {
                    order.setClientPrice(new BigDecimal(val));
                } catch (Exception e) {
                    // e.printStackTrace();
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 15:
                val = toNormalString(val);
                order.setIntermediaAccountNumber(val);
                break;
            case 16:
                try {
                    order.setBrokerPrice(new BigDecimal(val));
                } catch (Exception e) {
                    // e.printStackTrace();
                    setCellStyle(cell, wb);
                    formatError = true;
                }
                break;
            case 17:
                val = toNormalString(val);
                order.setExecuteBrokerAccountNumber(val);
                break;
            case 18:
                order.setExecuteBrokerAccountName(val);
                break;
            case 19:
                val = toNormalString(val);
                order.setClearingBrokerAccountNumber(val);
                break;
            case 20:
                order.setClearingBrokerAccountName(val);
                break;
            case 21:
                val = toNormalString(val);
                order.setIntroducingBrokerAccountNumber(val);
                break;
            case 22:
                order.setIntroducingBrokerAccountName(val);
                break;
            }
        }
        if (formatError) return null;
        return order;
    }

    private static void saveFillOrder(FilledOrder order, Row row, boolean withRowNumber) {
        if (order == null) return;

        for (int colIdx = 0; colIdx < FILL_ORDER_HEADER.length; ++colIdx) {
            Cell cell = row.createCell(colIdx);

            switch (cell.getColumnIndex()) {
            case 0:
                cell.setCellValue(FillOrderSupport.getTradeDateTime(order));
                break;
            case 1:
                cell.setCellValue(FillOrderSupport.getClientAccount(order));
                break;
            case 2:
                cell.setCellValue(FillOrderSupport.getClientName(order));
                break;
            case 3:
                cell.setCellValue(FillOrderSupport.getExchange(order));
                break;
            case 4:
                cell.setCellValue(FillOrderSupport.getProductCode(order));
                break;
            case 5:
                cell.setCellValue(FillOrderSupport.getProductName(order));
                break;
            case 6:
                cell.setCellValue(FillOrderSupport.getProductType(order));
                break;
            case 7:
                cell.setCellValue(FillOrderSupport.getContractMonthDate(order));
                break;
            case 8:
                cell.setCellValue(FillOrderSupport.getPutCallOption(order));
                break;
            case 9:
                cell.setCellValue(FillOrderSupport.getStrikePrice(order));
                break;
            case 10:
                cell.setCellValue(FillOrderSupport.getTradeType(order));
                break;
            case 11:
                cell.setCellValue(FillOrderSupport.getBusinessType(order));
                break;
            case 12:
                cell.setCellValue(FillOrderSupport.getBuySell(order));
                break;
            case 13:
                cell.setCellValue(FillOrderSupport.getQuantity(order));
                break;
            case 14:
                cell.setCellValue(FillOrderSupport.getPrice(order));
                break;
            case 15:
                cell.setCellValue(FillOrderSupport.getIntermediateAccount(order));
                break;
            case 16:
                cell.setCellValue(FillOrderSupport.getBrokerPrice(order));
                break;
            case 17:
                cell.setCellValue(FillOrderSupport.getExecutionBrokerAccount(order));
                break;
            case 18:
                cell.setCellValue(FillOrderSupport.getExecutionBrokerName(order));
                break;
            case 19:
                cell.setCellValue(FillOrderSupport.getClearingBrokerAccount(order));
                break;
            case 20:
                cell.setCellValue(FillOrderSupport.getClearingBrokerName(order));
                break;
            case 21:
                cell.setCellValue(FillOrderSupport.getIntroducingBrokerAccount(order));
                break;
            case 22:
                cell.setCellValue(FillOrderSupport.getIntroducingBrokerName(order));
                break;
            }
        }
        if (withRowNumber) {
            Cell cell = row.createCell(row.getLastCellNum());
            cell.setCellValue(order.getLastUpdateTime());
        }
    }

    private static String toNormalString(String str) {
        if (str.contains(".")) return str.substring(0, str.indexOf("."));
        return str;
    }

    private static String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:
            return cell.getNumericCellValue() + "";
        case HSSFCell.CELL_TYPE_STRING:
            return cell.getStringCellValue();
        case HSSFCell.CELL_TYPE_BOOLEAN:
            return cell.getBooleanCellValue() + "";
        case HSSFCell.CELL_TYPE_FORMULA:
            return cell.getCellFormula();
        default:
            return "";
        }
    }

    private static void setCellStyle(Cell cell, Workbook wb) {
        CellStyle style = wb.createCellStyle();

        style.setFillBackgroundColor(IndexedColors.BLUE.getIndex());
        style.setFillForegroundColor(IndexedColors.RED.getIndex());

        cell.setCellStyle(style);
    }

}
