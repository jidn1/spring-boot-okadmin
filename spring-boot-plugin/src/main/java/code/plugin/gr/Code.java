package code.plugin.gr;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 13:58
 * @Description:
 */
@Mojo(name = "code")
public class Code extends AbstractMojo {

    @Parameter(property = "project", required = true, readonly = true)
    private MavenProject project;

    public static Properties config;

    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("正经吉代码生成器启动中........");
        getLog().info("正经吉代码生成器启动中........");
        getLog().info("正经吉代码生成器启动中........");
        List<Resource> resources = this.project.getResources();

        String resourcePath = "";

        String javaPath = "";
        for (Resource resource : resources) {
            if (resource.getDirectory().contains("resources")) {
                resourcePath = resource.getDirectory();
            }
            if (resource.getDirectory().contains("java")) {
                javaPath = resource.getDirectory();
            }
        }
        config = new Properties();
        try {
            config.load(new FileReader(new File(resourcePath + "\\code.properties")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLog().info("************************************************************************");
        getLog().info("*****************************加载参数*************************************");
        getLog().info("CODE_COMPANY=" + config.getProperty("CODE_COMPANY"));

        String CODE_COMPANY = config.getProperty("CODE_COMPANY");
        if ((CODE_COMPANY == null) || ("".equals(CODE_COMPANY))) {
            config.put("CODE_COMPANY", "");
        }
        getLog().info("CODE_TABLE=" + config.getProperty("CODE_TABLE"));
        getLog().info("CODE_MODEL=" + config.getProperty("CODE_MODEL"));
        getLog().info("CODE_PACKAGE=" + config.getProperty("CODE_PACKAGE"));
        getLog().info("CODE_AUTH=" + config.getProperty("CODE_AUTH"));

        String staticName = config.getProperty("CODE_STATIC");
        getLog().info("CODE_STATIC=" + staticName);
        if ((staticName == null) || ("".equals(staticName))) {
            config.put("CODE_STATIC", "hurong_static");
        }
        config.put("CODE_JAVAPACKAGE", config.getProperty("CODE_PACKAGE").replace(".", "\\\\"));
        getLog().info("CODE_JAVAPACKAGE=" + config.getProperty("CODE_JAVAPACKAGE"));
        getLog().info("*****************************加载完毕*************************************");
        getLog().info("************************************************************************");


        Engine engine = new Engine(getLog());
        engine.engine(javaPath, resourcePath);
        getLog().info("------------------------------------------------------------------------");
        getLog().info("auth=ZJJ");
        getLog().info("version=1.0");
        getLog().info("@ALL，使用中遇到BUG联系zjjtv@gmail.com！");
        getLog().info("@ALL，使用中遇到BUG联系zjjtv@gmail.com！");
        getLog().info("@ALL，使用中遇到BUG联系zjjtv@gmail.com！");

    }
}
