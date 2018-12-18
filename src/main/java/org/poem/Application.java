package org.poem;

import org.poem.utils.VOCreateUtils;
import org.poem.utils.vo.PathVO;

import java.io.File;

/**
 * @author poem
 */
public class Application {

    public static void main(String[] args) throws Exception {
        VOCreateUtils.getCreateVOAndService(new File("platform.xls"),
                new PathVO().withPath("src/main/java").whthPackage("org.poem"));
    }
}
