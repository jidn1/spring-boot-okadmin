package util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/18 14:28
 * @Description:
 */
public class DocumentHandler {

    private Configuration configuration = null;

    public DocumentHandler(String ftlSrc) {
        this.configuration = new Configuration();
        this.configuration.setDefaultEncoding("utf-8");
        try {
            if (!"".equals(ftlSrc)) {
                this.configuration.setDirectoryForTemplateLoading(new File(ftlSrc));
            } else {
                this.configuration.setClassForTemplateLoading(getClass(), "/resources");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createOrderAndDown(Map<String, Object> dataMap, String fileSrc, String ftlName) {
        Template t = null;
        Writer out = null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            File outFile = new File(fileSrc);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            t = this.configuration.getTemplate(ftlName);

            fileOutputStream = new FileOutputStream(outFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
            out = new BufferedWriter(outputStreamWriter);

            t.process(dataMap, out);

            out.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createDoc(Map<String, Object> dataMap, String fileSrc) {
        Template t = null;
        try {
            t = this.configuration.getTemplate("firstCreditor.ftl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File outFile = new File(fileSrc);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));

            t.process(dataMap, out);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
