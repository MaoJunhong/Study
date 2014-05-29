package com.atplatform.rcp.plugin.part.port;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.nebula.widgets.nattable.data.IRowDataProvider;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class PortExcelImpl {

    public static void exportExcel(IRowDataProvider<?> data, String fileName, boolean isE2007, String[] columnNames) throws Exception {

        Workbook wb = null;
        if (isE2007) {
            wb = new XSSFWorkbook(); // 2007
        } else {
            wb = new HSSFWorkbook(); // 2003
        }
        Sheet sheet = wb.createSheet();
        CellStyle style = wb.createCellStyle();

        int colCount = data.getColumnCount();
        int rowCount = data.getRowCount();
        int headCount = 0;

        // export head
        if (columnNames != null && columnNames.length >= colCount) {
            Row head = sheet.createRow(0);
            ++headCount;
            for (int colIdx = 0; colIdx < colCount; ++colIdx) {
                Cell cell = head.createCell(colIdx);
                cell.setCellStyle(style);
                cell.setCellValue(columnNames[colIdx]);
            }
        }

        // export content
        for (int rowIdx = 0; rowIdx < rowCount; ++rowIdx) {
            Row row = sheet.createRow(rowIdx + headCount);

            for (int colIdx = 0; colIdx < colCount; ++colIdx) {
                Cell cell = row.createCell(colIdx);
                cell.setCellStyle(style);
                Object obj = data.getDataValue(colIdx, rowIdx);
                if (obj == null) obj = "";
                cell.setCellValue(obj.toString());
            }
        }

        for (int colIdx = 0; colIdx < colCount; ++colIdx) {
            sheet.autoSizeColumn(colIdx);
        }

        if (isE2007) {
            fileName += ".xlsx";
        } else {
            fileName += ".xls";
        }
        wb.write(new FileOutputStream(fileName));
    }

    public static void exportExcel(Table table, String fileName, boolean isE2007) throws Exception {

        Workbook wb = null;
        if (isE2007) {
            wb = new XSSFWorkbook(); // 2007
        } else {
            wb = new HSSFWorkbook(); // 2003
        }
        Sheet sheet = wb.createSheet();
        CellStyle style = wb.createCellStyle();

        // export head
        Row head = sheet.createRow(0);
        for (int colIdx = 0; colIdx < table.getColumnCount(); ++colIdx) {
            Cell cell = head.createCell(colIdx);
            cell.setCellStyle(style);
            cell.setCellValue(table.getColumn(colIdx).getText());
        }

        // export content
        for (int rowIdx = 0; rowIdx < table.getItemCount(); ++rowIdx) {
            TableItem item = table.getItem(rowIdx);
            Row row = sheet.createRow(rowIdx + 1);

            for (int colIdx = 0; colIdx < table.getColumnCount(); ++colIdx) {
                Cell cell = row.createCell(colIdx);
                cell.setCellStyle(style);
                cell.setCellValue(item.getText(colIdx));
            }
        }

        for (int colIdx = 0; colIdx < table.getColumnCount(); ++colIdx) {
            sheet.autoSizeColumn(colIdx);
        }

        if (isE2007) {
            fileName += ".xlsx";
        } else {
            fileName += ".xls";
        }
        wb.write(new FileOutputStream(fileName));
    }

}
