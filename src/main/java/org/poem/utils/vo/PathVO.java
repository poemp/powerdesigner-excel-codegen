package org.poem.utils.vo;

/**
 * @author poem
 */
public class PathVO {

    /**
     * 路径
     */
    private String path;

    /**
     * 包
     */
    private String packages;


    public PathVO withPath(String path) {
        setPath(path);
        return this;
    }

    public PathVO whthPackage(String packages) {
        setPackages(packages);
        return this;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }
}
