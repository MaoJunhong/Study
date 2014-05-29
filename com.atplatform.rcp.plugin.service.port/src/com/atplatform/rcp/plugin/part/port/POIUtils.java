package com.atplatform.rcp.plugin.part.port;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class POIUtils {
    public static void copyCellStyle(CellStyle cellStyle, CellStyle newstyle) {
        newstyle.setAlignment(cellStyle.getAlignment());
        newstyle.setBorderBottom(cellStyle.getBorderBottom());
        newstyle.setBorderLeft(cellStyle.getBorderLeft());
        newstyle.setBorderRight(cellStyle.getBorderRight());
        newstyle.setBorderTop(cellStyle.getBorderTop());
        newstyle.setTopBorderColor(cellStyle.getTopBorderColor());
        newstyle.setBottomBorderColor(cellStyle.getBottomBorderColor());
        newstyle.setRightBorderColor(cellStyle.getRightBorderColor());
        newstyle.setLeftBorderColor(cellStyle.getLeftBorderColor());

        newstyle.setFillBackgroundColor(cellStyle.getFillBackgroundColor());
        newstyle.setFillForegroundColor(cellStyle.getFillForegroundColor());

        newstyle.setDataFormat(cellStyle.getDataFormat());
        newstyle.setFillPattern(cellStyle.getFillPattern());
        newstyle.setHidden(cellStyle.getHidden());
        newstyle.setIndention(cellStyle.getIndention());
        newstyle.setLocked(cellStyle.getLocked());
        newstyle.setRotation(cellStyle.getRotation());
        newstyle.setVerticalAlignment(cellStyle.getVerticalAlignment());
        newstyle.setWrapText(cellStyle.getWrapText());

    }

    public static void copyRow(Workbook wb, Row srow, Row row, boolean copyValueFlag) {
        for (Iterator<Cell> cellIt = srow.cellIterator(); cellIt.hasNext();) {
            Cell tmpCell = cellIt.next();
            Cell newCell = row.createCell(tmpCell.getColumnIndex());
            copyCell(wb, tmpCell, newCell, copyValueFlag);
        }
    }

    public static void copyCell(Workbook wb, Cell tmpCell, Cell newCell, boolean copyValueFlag) {
        CellStyle newstyle = wb.createCellStyle();
        copyCellStyle(tmpCell.getCellStyle(), newstyle);
        newCell.setCellStyle(newstyle);
        if (tmpCell.getCellComment() != null) {
            newCell.setCellComment(tmpCell.getCellComment());
        }
        int srcCellType = tmpCell.getCellType();
        newCell.setCellType(srcCellType);
        if (copyValueFlag) {
            if (srcCellType == HSSFCell.CELL_TYPE_NUMERIC) {
                if (HSSFDateUtil.isCellDateFormatted(tmpCell)) {
                    newCell.setCellValue(tmpCell.getDateCellValue());
                } else {
                    newCell.setCellValue(tmpCell.getNumericCellValue());
                }
            } else if (srcCellType == HSSFCell.CELL_TYPE_STRING) {
                newCell.setCellValue(tmpCell.getStringCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_BLANK) {
            } else if (srcCellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                newCell.setCellValue(tmpCell.getBooleanCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_ERROR) {
                newCell.setCellErrorValue(tmpCell.getErrorCellValue());
            } else if (srcCellType == HSSFCell.CELL_TYPE_FORMULA) {
                newCell.setCellFormula(tmpCell.getCellFormula());
            } else {
            }
        }
    }
}