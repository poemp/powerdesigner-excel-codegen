package org.poem.utils.excel;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.poem.utils.vo.TableVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author poem
 */

public class ExcelUtils {

    /**
     * 读取原始数据，返回json
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static List<TableVO> readExcel(File file) throws IOException {
        Workbook workbook = null;

        List<TableVO> tableVOS = Lists.newArrayList();
        int index = 0;
        try {
            if (file.getName().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(file);
            } else {
                workbook = new HSSFWorkbook(new FileInputStream(file));
            }
            // -Xms2048M -Xmx4096M -XX:MaxMetaspaceSize=1024m
            Sheet sheet = workbook.getSheetAt(1);
            Iterator<Cell> cellIterator;
            Iterator<Row> rowIterator;
            Row row;
            Cell cell;
            JSONObject jsonObject;
            rowIterator = sheet.rowIterator();
            List<String> header = Lists.newArrayList();
            TableVO tableVO = new TableVO();
            List<JSONObject> jsonObjects = Lists.newArrayList();
            while (rowIterator.hasNext()) {
                row = rowIterator.next();
                if (null != row.getCell(0)) {
                } else {
                    index = -1;
                    if(jsonObjects.size() > 0){
                        tableVO.setFiled(new ArrayList<>(jsonObjects));
                        tableVOS.add(tableVO);
                    }
                    jsonObjects.clear();
                    tableVO = new TableVO();

                }
                cellIterator = row.cellIterator();
                jsonObject = new JSONObject();
                int x = 0;
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    if (index == 0) {
                        if (x == 1) {
                            tableVO.setTableName(cell.getStringCellValue());
                        } else if (x == 2) {
                            tableVO.setChineseName(cell.getStringCellValue());
                        }
                    }
                    if (index == 1) {
                        //无条件转为文本，迫不得已
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        if (!header.contains(cell.getStringCellValue().trim())) {
                            header.add(cell.getStringCellValue().trim().replaceAll("\\s*", ""));
                        }
                    } else if (index >= 2) {
                        String vcalue;
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_STRING:
                                vcalue = cell.getStringCellValue();
                                break;
                            case Cell.CELL_TYPE_BOOLEAN:
                                Boolean val1 = cell.getBooleanCellValue();
                                vcalue = val1.toString();
                                break;
                            case Cell.CELL_TYPE_NUMERIC:
                                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                    Date theDate = cell.getDateCellValue();
                                    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                                    vcalue = dff.format(theDate);
                                } else {
                                    DecimalFormat df = new DecimalFormat("0");
                                    vcalue = df.format(cell.getNumericCellValue());
                                }
                                break;
                            case Cell.CELL_TYPE_BLANK:
                                vcalue = "";
                                break;
                            default:
                                //无条件转为文本，迫不得已
                                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                vcalue = cell.getStringCellValue();
                        }
                        jsonObject.put(header.get(cell.getColumnIndex()), vcalue);
                    }
                    x++;
                }
                if (jsonObject.size() > 0) {
                    jsonObjects.add(jsonObject);
                }
                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }

        return tableVOS;
    }

}
