package ${CODE_PACKAGE!}.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.util.HttpServletRequestUtils;
import com.common.model.JsonResult;
import com.common.model.PageResult;
import io.swagger.annotations.Api;
import com.db.Criteria;
import org.springframework.web.bind.annotation.*;
import ${CODE_PACKAGE!}.model.${CODE_MODEL!};
import ${CODE_PACKAGE!}.service.${CODE_MODEL!}Service;
import java.util.Map;

/**
 * @Copyright:   正经吉
 * @author:      ${codeVersion!}
 * @version:     V1.0
 * @Date:        ${codeDate!}
 */
@Api(value = "")
@RestController
@RequestMapping("/${CODE_MODEL_SMALL!}")
public class ${CODE_MODEL!}Controller{

	@Resource
	private ${CODE_MODEL!}Service ${CODE_MODEL_SMALL!}Service;

	@RequestMapping("/list")
	@ResponseBody
	public PageResult list(HttpServletRequest request) {
		Map<String, String> params = HttpServletRequestUtils.getParams(request);
		return ${CODE_MODEL_SMALL!}Service.findPageBySql(params);
	}


    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(${CODE_MODEL!} ${CODE_MODEL_SMALL!}) {
        Criteria<${CODE_MODEL!},Integer> criteria = new Criteria<>(${CODE_MODEL!}.class);
        criteria.save(${CODE_MODEL_SMALL!});
        return new JsonResult().setSuccess(true).setMsg("success");
    }

	@RequestMapping("/modify")
	@ResponseBody
	public JsonResult modify(HttpServletRequest request,${CODE_MODEL!} ${CODE_MODEL_SMALL!}) {
		Criteria<${CODE_MODEL!},Integer> criteria = new Criteria<>(${CODE_MODEL!}.class);
		criteria.update(${CODE_MODEL_SMALL!});
		return new JsonResult().setSuccess(true).setMsg("success");
	}


	@RequestMapping("/remove")
	@ResponseBody
	public JsonResult remove(String idsStr) {
		Criteria<${CODE_MODEL!},Integer> criteria = new Criteria<>(${CODE_MODEL!}.class);
        String[] idss = idsStr.split(",");
        for(String id : idss){
			criteria.deleteByPrimary(Integer.valueOf(id));
        }
		return new JsonResult().setSuccess(true).setMsg("success");
	}

}
