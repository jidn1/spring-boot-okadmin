#### 介绍
spring-boot-core 是一个基础核心包，封装了基础的增、删、改、查（分页查询）功能，
封装了 quartz 定时器，以及授权功能，代码混淆(allatori)

#### 基础环境
本项目基于 spring boot     JDK1.7以上

#### 使用方法
`

    <dependency>
        <groupId>com.github.zjjtv</groupId>
        <artifactId>spring-boot-core</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </dependency>
`

- 实体继承

``
    public class TopHot extends BaseModel
``

``
    public interface TopHotDao extends  BaseDao<TopHot, Integer> {
``

然后就可以使用 `Criteria` 执行器进行操作了


`Criteria` oh-my-spring 中的一个核心操作器 基于Mybatis `Example.Criteria` 

功能有  
- 增
- 删
- 改
- 查 (单表分页查询,条件查询,模糊查询,in 语句等)
 

例子

- 增

``
  
      public void save(TopHot topHot) {
          Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
          criteria.save(topHot);
         
      }
      
      
      
``

- 改

``

    public void modify(HttpServletRequest request, TopHot topHot) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        criteria.update(topHot);
       
    }
``

- 删

``

    public void remove(String idsStr) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        String[] idss = idsStr.split(",");
        for (String id : idss) {
            criteria.deleteByPrimary(Integer.valueOf(id));
        }
    }

``

- 查

``

    public void list(String idsStr) {
        Criteria<TopHot, Integer> criteria = new Criteria<>(TopHot.class);
        criteria.addFilter("title_like","%tom%");
        criteria.addFilter("id=",1);
        criteria.addFilter("id_in","1,2");
        criteria.addFilter("type_gt",1);
        List<TopHot> list = criteria.findListByExample();
    }

``

- 分页 

``

    public PageResult findPageBySql(HttpServletRequest request) {
         Map<String, String> params = HttpServletRequestUtils.getParams(request);
        Criteria<TopHot,Long> criteria = new Criteria<>(TopHot.class);
        Page<TopHot> page = PageFactory.getPage(param);
        criteria.findAll();
        return new PageResult(page,page.getTotal(), page.getPages(), page.getPageSize());
    }

``

`QuartzManager` oh-my-spring 中的一个核心操作器 基于 quartz 框架定时器 

使用方法

``
        
        QuartzManager.removeJob("initCnBlogJob");
        ScheduleJob initCnBlogJob = new ScheduleJob();
        initCnBlogJob.setJobName("initCnBlogJob");
        initCnBlogJob.setBeanName("topHotService");
        initCnBlogJob.setMethodName("crawlerCnBlog");
        initCnBlogJob.setCronExpression("5 0/5 * * * ?");
        QuartzManager.addJob(initCnBlogJob);

`` 


#### 推荐

- [自动生成代码插件](https://github.com/jidn1/spring-boot-okadmin/spring-boot-plugin)
- [简易版后台管理系统](https://github.com/jidn1/spring-boot-okadmin/spring-boot-admin)
- [简易版聊天室(支持发送语音，视频)](https://github.com/jidn1/spring-boot-okadmin/spring-boot-chat)
- [前台视频网站](https://github.com/jidn1/spring-boot-okadmin/spring-boot-video)
- [前台视频网站(vue页面)](https://github.com/jidn1/spring-boot-okadmin/vue-movie-master)

