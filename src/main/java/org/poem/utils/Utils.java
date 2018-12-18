package org.poem.utils;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static Pattern COMPILE = Pattern.compile("^(\\d+)(\\.0*)?$");

    /**
     * 获取excel的值
     *
     * @param c
     * @return
     */
    static public String getCellValue(Cell c) {
        String o;
        switch (c.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                o = "";
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                o = String.valueOf(c.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                o = String.valueOf(c.getCellFormula());
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(c)) {
                    Date cellValue = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(c.getNumericCellValue());
                    o = DateFormatUtils.format(cellValue, "yyyy-MM-dd HH:mm:ss");
                } else {
                    o = String.valueOf(c.getNumericCellValue());
                    o = matchDoneBigDecimal(o);
                    o = converNumByReg(o);
                }
                break;
            case Cell.CELL_TYPE_STRING:
                o = c.getStringCellValue();
                break;
            default:
                o = null;
                break;
        }
        return o;
    }

    /**
     * 对科学计数法进行处理
     *
     * @param bigDecimal
     * @return
     */
    private static String matchDoneBigDecimal(String bigDecimal) {
        // 对科学计数法进行处理
        boolean flg = Pattern.matches("^-?\\d+(\\.\\d+)?(E-?\\d+)?$", bigDecimal);
        if (flg) {
            BigDecimal bd = new BigDecimal(bigDecimal);
            bigDecimal = bd.toPlainString();
        }
        return bigDecimal;
    }

    /**
     * 小数点的验证
     *
     * @param number
     * @return
     */
    public static String converNumByReg(String number) {
        Matcher matcher = COMPILE.matcher(number);
        while (matcher.find()) {
            number = matcher.group(1);
        }
        return number;
    }
}
