package com.ms_demo.common;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelDataHeleper {
    @DataProvider(name = "excel")
    public Iterator<Object[]> dataMethod(Method m) {
        DataFile d = m.getAnnotation(DataFile.class);
        List<Object[]> item = new ArrayList<>();
        List<List<String>> list = read(d.path(), d.sheet());
        if (list!= null && list.size() > 0) {
            int size = list.get(0).size();
            for (int i = 1; i < list.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < size; j++) {
                    map.put(list.get(0).get(j), list.get(i).get(j));
                }
                item.add(new Object[]{map});
            }
        }
        return item.iterator();
    }

    private List<List<String>> read(String path, String sheetName) {
        try {
            Workbook workbook = WorkbookFactory.create(new File(path));
            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
            List<List<String>> data = new ArrayList<>();
            for (int i = 0; i < rowCount; i++) {
                List<String> rowData = new ArrayList<>();
                for (int j = 0; j < colCount; j++) {
                    Cell cell = sheet.getRow(i).getCell(j);
                    rowData.add(getCellValue(cell));
                }
                data.add(rowData);
            }
            workbook.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }
}
