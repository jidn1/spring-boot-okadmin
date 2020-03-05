package code.plugin.gr;


import java.util.HashMap;
import java.util.Map;

import jdbc.DBUtils;
import model.Table;
import org.apache.maven.plugin.logging.Log;
import util.DateUtils;
import util.DocumentHandler;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:16
 * @Description:
 */
public class Engine {


    private Log log;

    public Engine(Log log) {
        this.log = log;
    }

    private Engine() {
    }

    public void engine(String javaPath, String resourcePath) {
        mapper(resourcePath);
        model(resourcePath);
        dao(resourcePath);
        service(resourcePath);
        serviceimpl(resourcePath);
        controller(resourcePath);

        modelJs(resourcePath);
        htmlList(resourcePath);
        htmlAdd(resourcePath);
        htmlModify(resourcePath);
    }


    public static String getProjectName() {
        String pack = Code.config.getProperty("CODE_PACKAGE");
        String[] split = pack.split("\\.");
        return split[1];
    }

    public static String getModelLC() {
        return Code.config.getProperty("CODE_MODEL").toLowerCase();
    }

    public static String getModelFUC() {
        String lowerCase = Code.config.getProperty("CODE_MODEL").toLowerCase();
        return lowerCase.substring(0, 1).toUpperCase() + lowerCase.substring(1);
    }

    public static String getRequestMapping() {
        return getRequestMappingPR() + getModelLC();
    }

    public static String getPath() {
        String packagePath = Code.config.getProperty("CODE_PACKAGE");
        String pack = packagePath.substring(packagePath.indexOf(".") + 1);

        String[] split = pack.split("\\.");
        String rm = "/";
        for (int i = 0; i < split.length; i++) {
            rm = rm + split[i];
            rm = rm + "/";
        }
        return rm;
    }

    public static String getRequestMappingPR() {
        String pack = Code.config.getProperty("CODE_PACKAGE");
        String[] split = pack.split("\\.");
        String rm = "/";
        for (int i = 0; i < split.length; i++) {
            if (i > 1) {
                rm = rm + split[i];
                rm = rm + "/";
            }
        }
        return rm;
    }

    public void controller(String javaPath) {
        this.log.info("***************************生成controller********************************");


        Map<String, Object> map = new HashMap();
        map.put("CODE_COMPANY", Code.config.getProperty("CODE_COMPANY"));
        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));
        map.put("CODE_MODEL_LC", getModelLC());

        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));


        map.put("RequestMapping", getRequestMapping());


        map.put("RequestMappingPR", getPath());

        map.put("CODE_TABLE", Code.config.getProperty("CODE_TABLE"));
        map.put("codeVersion", Code.config.getProperty("CODE_AUTH"));
        map.put("codeDate", DateUtils.getDate());

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("resources", "java");
        String javaName = modelJsPath + "\\" + Code.config.getProperty("CODE_JAVAPACKAGE") + "\\controller\\" + Code.config.getProperty("CODE_MODEL") + "Controller.java";
        this.log.info(javaName);
        documentHandler.createOrderAndDown(map, javaName, "controller.ftl");
        this.log.info("************************************************************************");
    }

    public void serviceimpl(String javaPath) {
        this.log.info("***************************生成service-impl******************************");


        Map<String, Object> map = new HashMap();
        map.put("CODE_COMPANY", Code.config.getProperty("CODE_COMPANY"));
        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));

        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));

        map.put("CODE_TABLE", Code.config.getProperty("CODE_TABLE"));
        map.put("codeVersion", Code.config.getProperty("CODE_AUTH"));
        map.put("codeDate", DateUtils.getDate());

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("resources", "java");
        String javaName = modelJsPath + "\\" + Code.config.getProperty("CODE_JAVAPACKAGE") + "\\service\\impl\\" + Code.config.getProperty("CODE_MODEL") + "ServiceImpl.java";
        this.log.info(javaName);
        documentHandler.createOrderAndDown(map, javaName, "serviceimpl.ftl");
        this.log.info("************************************************************************");
    }

    public void service(String javaPath) {
        this.log.info("***************************生成service***********************************");


        Map<String, Object> map = new HashMap();
        map.put("CODE_COMPANY", Code.config.getProperty("CODE_COMPANY"));
        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));
        map.put("CODE_TABLE", Code.config.getProperty("CODE_TABLE"));
        map.put("codeVersion", Code.config.getProperty("CODE_AUTH"));
        map.put("codeDate", DateUtils.getDate());

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("resources", "java");
        String javaName = modelJsPath + "\\" + Code.config.getProperty("CODE_JAVAPACKAGE") + "\\service\\" + Code.config.getProperty("CODE_MODEL") + "Service.java";
        this.log.info(javaName);
        documentHandler.createOrderAndDown(map, javaName, "service.ftl");
        this.log.info("************************************************************************");
    }

    public void dao(String javaPath) {
        this.log.info("***************************生成dao***************************************");


        Map<String, Object> map = new HashMap();
        map.put("CODE_COMPANY", Code.config.getProperty("CODE_COMPANY"));
        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));
        map.put("CODE_TABLE", Code.config.getProperty("CODE_TABLE"));
        map.put("codeVersion", Code.config.getProperty("CODE_AUTH"));
        map.put("codeDate", DateUtils.getDate());

        DocumentHandler documentHandler = new DocumentHandler("");
        String modelJsPath = javaPath.replace("resources", "java");
        String javaName = modelJsPath + "\\" + Code.config.getProperty("CODE_JAVAPACKAGE") + "\\dao\\" + Code.config.getProperty("CODE_MODEL") + "Dao.java";
        this.log.info(javaName);
        documentHandler.createOrderAndDown(map, javaName, "dao.ftl");
        this.log.info("************************************************************************");
    }

    public void model(String javaPath) {
        this.log.info("***************************生成mode**************************************");
        Table tabel = DBUtils.getTabel(Code.config.getProperty("CODE_TABLE"));


        Map<String, Object> map = new HashMap();
        map.put("CODE_COMPANY", Code.config.getProperty("CODE_COMPANY"));
        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));
        map.put("CODE_TABLE", Code.config.getProperty("CODE_TABLE"));
        map.put("codeVersion", Code.config.getProperty("CODE_AUTH"));
        map.put("codeDate", DateUtils.getDate());
        map.put("columns", tabel.getColumns());
        map.put("columnsType", tabel.getColumnsType());

        DocumentHandler documentHandler = new DocumentHandler("");

        String[] split = javaPath.split("\\\\");
        int index = 0;
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("src")) {
                index = i - 1;
                break;
            }
        }
        String modelPath = "";
        for (int i = 0; i < split.length; i++) {
            if (i == index) {
                modelPath = modelPath + "hurong_model";
            } else {
                modelPath = modelPath + split[i];
            }
            if (i != split.length - 1) {
                modelPath = modelPath + "\\\\";
            }
        }

        String modelJsPath = javaPath.replace("resources", "java");
        String javaName = modelJsPath + "\\" + Code.config.getProperty("CODE_JAVAPACKAGE") + "\\model\\" + Code.config.getProperty("CODE_MODEL") + ".java";
        this.log.info(javaName);
        documentHandler.createOrderAndDown(map, javaName, "model.ftl");
        this.log.info("************************************************************************");
    }

    public void mapper(String javaPath) {
        DocumentHandler documentHandler = new DocumentHandler("");
        Map<String, Object> map = new HashMap();

        this.log.info("***************************生成mapper.xml********************************");

        map.put("CODE_PACKAGE", Code.config.getProperty("CODE_PACKAGE"));
        map.put("CODE_MODEL", Code.config.getProperty("CODE_MODEL"));

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath = modelJsPath + "\\mappers\\" + Code.config.getProperty("CODE_MODEL") + "Mapper.xml";
        this.log.info(modelJsPath);
        documentHandler.createOrderAndDown(map, modelJsPath, "mapper.ftl");
        this.log.info("************************************************************************");
    }


    public void modelJs(String javaPath) {
        this.log.info("***************************生成JavaSrcipt**************************************");
        Table tabel = DBUtils.getTabel(Code.config.getProperty("CODE_TABLE"));


        Map<String, Object> map = new HashMap();
        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));
        map.put("CODE_MODEL_SMALL_lOW",getModelLC());
        map.put("columns", tabel.getColumns());
        map.put("columnsType", tabel.getColumnsType());


        DocumentHandler documentHandler = new DocumentHandler("");

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath = modelJsPath + "\\static\\js\\modules\\";

        String htmlName = modelJsPath + Code.config.getProperty("CODE_MODEL") + ".js";
        this.log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "modelJs.ftl");
        this.log.info("************************************************************************");
    }


    public void htmlList(String javaPath) {
        this.log.info("***************************生成htmlList**************************************");
        Table tabel = DBUtils.getTabel(Code.config.getProperty("CODE_TABLE"));


        Map<String, Object> map = new HashMap();
        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));
        map.put("columns", tabel.getColumns());
        map.put("columnsType", tabel.getColumnsType());


        DocumentHandler documentHandler = new DocumentHandler("");

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath = modelJsPath + "\\templates\\view\\";

        String htmlName = modelJsPath + getModelLC() + "list.html";
        this.log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "htmllist.ftl");
        this.log.info("************************************************************************");
    }

    public void htmlAdd(String javaPath) {
        this.log.info("***************************生成htmlAdd**************************************");
        Table tabel = DBUtils.getTabel(Code.config.getProperty("CODE_TABLE"));

        Map<String, Object> map = new HashMap();
        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));

        map.put("columns", tabel.getColumns());
        map.put("columnsType", tabel.getColumnsType());


        DocumentHandler documentHandler = new DocumentHandler("");


        String modelJsPath = javaPath.replace("java", "");
        modelJsPath = modelJsPath + "\\templates\\view\\";

        String htmlName = modelJsPath + getModelLC() + "add.html";
        this.log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "htmladd.ftl");
        this.log.info("************************************************************************");
    }


    public void htmlModify(String javaPath) {
        this.log.info("***************************生成htmlModify**************************************");
        Table tabel = DBUtils.getTabel(Code.config.getProperty("CODE_TABLE"));

        Map<String, Object> map = new HashMap();
        map.put("CODE_MODEL_SMALL", Code.config.getProperty("CODE_MODEL").substring(0, 1).toLowerCase() + Code.config.getProperty("CODE_MODEL").substring(1));
        map.put("columns", tabel.getColumns());
        map.put("columnsType", tabel.getColumnsType());

        DocumentHandler documentHandler = new DocumentHandler("");

        String modelJsPath = javaPath.replace("java", "");
        modelJsPath = modelJsPath + "\\templates\\view\\";

        String htmlName = modelJsPath + getModelLC() + "modify.html";
        this.log.info(htmlName);
        documentHandler.createOrderAndDown(map, htmlName, "htmlmodify.ftl");
        this.log.info("************************************************************************");
    }

}
