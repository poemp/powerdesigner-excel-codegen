package org.poem.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.poem.utils.excel.ExcelUtils;
import org.poem.utils.vo.PathVO;
import org.poem.utils.vo.TableVO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.poem.utils.Strings.*;

/**
 * @author poem
 */
public class VOCreateUtils {


    /**
     * 创建文件目录
     *
     * @param path
     * @throws IOException
     */
    private static void createFile(String path) throws IOException {
        //文件存放path

        File file = new File(path);
        //首先看父类文件夹有没有

        File fileParent = file.getParentFile();
        //首先创建父类文件夹
        if (!fileParent.exists()) {

            fileParent.mkdirs();      //为什么用mkdirs()呢？因为这个方法可以在不知道偶没有父类文件夹的情况下，创建文件夹，而mkdir（）必须在有父类的文件夹下创建文件
        }
        file.createNewFile();
    }

    /**
     * 创建vo
     *
     * @param table
     * @param pathVO
     * @throws IOException
     */
    private static void createVO(TableVO table, PathVO pathVO) throws IOException {
        String sheetChineseName = table.getChineseName();
        String tableName = table.getTableName();
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("package " + pathVO.getPackages() + ";\n" +
                "\n" +
                "import io.swagger.annotations.Api;\n" +
                "import io.swagger.annotations.ApiModelProperty;\n" +
                "/**\n" +
                " * " + sheetChineseName + "\n" +
                " * @author poem\n" +
                " */\n" +
                "@Api(value = \"" + sheetChineseName + "\")\n");
        sqlBuf.append("public class " + TableName(tableName) + "VO {\n\n");
        List<String> fields = new ArrayList<>();
        for (JSONObject jsonObject : table.getFiled()) {

            fields.add(
                    "    @ApiModelProperty(value = \"" + String.join("-", jsonObject.getString("字段中文名")).replaceAll(" +", "-") + "\")\n" +
                            "    private String " + fieldName(jsonObject.getString("字段英文名")) + ";\n");
        }
        sqlBuf.append(String.join("\n", fields));
        sqlBuf.append("\n}\n");
        String path = pathVO.getPath() + File.separator + pathVO.getPackages().replaceAll("\\.", File.separator)
                + File.separator + "vo" + File.separator + getPath(tableName) + File.separator + TableName(tableName) + "VO.java";
        createFile(path);
        IOUtils.write(sqlBuf, new FileOutputStream(new File(path)));
    }


    /**
     * 创建vo
     *
     * @param table
     * @param pathVO
     * @throws IOException
     */
    private static void createService(TableVO table, PathVO pathVO) throws IOException {
        String sheetChineseName = table.getChineseName();
        String tableName = table.getTableName();
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("package " + pathVO.getPackages() + ";\n" +
                "\n" +
                "import org.springframework.stereotype.Service;\n" +
                "/**\n" +
                " * " + sheetChineseName + "\n" +
                " * @author poem\n" +
                " */\n" +
                "@Service\n");
        sqlBuf.append("public class " + TableName(tableName) + "Service {\n\n");
        sqlBuf.append("\n}\n");
        String path = pathVO.getPath() + File.separator + pathVO.getPackages().replaceAll("\\.", File.separator)
                + File.separator + "service" + File.separator + getPath(tableName) + File.separator + TableName(tableName) + "Service.java";
        createFile(path);
        IOUtils.write(sqlBuf, new FileOutputStream(new File(path)));
    }

    /**
     * 创建vo
     *
     * @param table
     * @param pathVO
     * @throws IOException
     */
    private static void createController(TableVO table, PathVO pathVO) throws IOException {
        String sheetChineseName = table.getChineseName();
        String tableName = table.getTableName();
        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("package " + pathVO.getPackages() + ";\n" +
                "\n" +
                "import io.swagger.annotations.Api;\n" +
                "import org.springframework.web.bind.annotation.RequestMapping;\n" +
                "import org.springframework.web.bind.annotation.RestController;\n" +
                "/**\n" +
                " * " + sheetChineseName + "\n" +
                " * @author poem\n" +
                " */\n" +
                "@RestController\n" +
                "@RequestMapping(\"/v1/" + getPath(tableName) + "\")\n" +
                "@Api(value = \"/v1/" + getPath(tableName) + "\",tags = {\"" + sheetChineseName + "\"})\n");
        sqlBuf.append("public class " + TableName(tableName) + "Controller {\n\n");
        sqlBuf.append("\n}\n");
        String path = pathVO.getPath() + File.separator + pathVO.getPackages().replaceAll("\\.", File.separator)
                + File.separator + "controller" + File.separator + getPath(tableName) + File.separator + TableName(tableName) + "Controller.java";
        createFile(path);
        IOUtils.write(sqlBuf, new FileOutputStream(new File(path)));
    }

    /**
     * 创建VO
     *
     * @throws Exception
     */
    public static void getCreateVOAndService(File file, PathVO pathVO) throws Exception {

        List<TableVO> tables = ExcelUtils.readExcel(file);
        for (TableVO table : tables) {
            createVO(table, pathVO);
//            createController(table, pathVO);
//            createService(table, pathVO);
        }

    }
}
