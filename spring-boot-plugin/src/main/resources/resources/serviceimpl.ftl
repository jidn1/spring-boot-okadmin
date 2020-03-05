package ${CODE_PACKAGE!}.service.impl;

import ${CODE_PACKAGE!}.model.${CODE_MODEL!};
import ${CODE_PACKAGE!}.service.${CODE_MODEL!}Service;
import com.db.Criteria;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.common.model.PageResult;
import com.util.PageFactory;
import com.github.pagehelper.Page;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> ${CODE_MODEL!}Service </p>
 * @author:  ${codeVersion!}
 * @Date :   ${codeDate!}
 */
@Service("${CODE_MODEL_SMALL!}Service")
public class ${CODE_MODEL!}ServiceImpl implements ${CODE_MODEL!}Service{
	

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Criteria<${CODE_MODEL!},Integer> criteria = new Criteria<>(${CODE_MODEL!}.class);
        Page<${CODE_MODEL!}> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

}
