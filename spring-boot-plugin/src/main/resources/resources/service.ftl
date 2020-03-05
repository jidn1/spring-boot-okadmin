package ${CODE_PACKAGE!}.service;


import ${CODE_PACKAGE!}.model.${CODE_MODEL!};
import com.common.model.PageResult;
import java.util.Map;

/**
 * <p> ${CODE_MODEL!}Service </p>
 * @author:         ${codeVersion!}
 * @Date :          ${codeDate!}
 */
public interface ${CODE_MODEL!}Service {

   PageResult findPageBySql(Map<String,String> param);
}
