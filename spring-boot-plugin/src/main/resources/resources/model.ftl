package ${CODE_PACKAGE!}.model;

<#if columnsType ?? >
<#list columnsType as ct > 
<#if ct=="BigDecimal" >
import java.math.BigDecimal;
<#elseif  ct=="Date" >
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
<#else>
</#if>
</#list>
</#if>
import lombok.Data;
import com.mvc.base.model.BaseModel;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * <p> ${CODE_MODEL!} </p>
 * @author:         ${codeVersion!}
 * @Date :          ${codeDate!} 
 */
@Data
@Table(name="${CODE_TABLE!}")
public class ${CODE_MODEL!} extends BaseModel {
	
	
	<#if columns ?? >
	<#list columns as c >
	/**
	 * ${(c.comments)!}
	 */
	<#if c.key=="PRI">
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "${(c.column)!}", unique = true, nullable = false)
	<#elseif  c.javatype=="Date" >
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name= "${(c.column)!}")
	<#else>
	@Column(name= "${(c.column)!}")
	</#if>
	private ${(c.javatype)!} ${(c.column)!};
	
	</#list>
	</#if>
	
	
}
