package com.movie.service.impl;

import com.common.model.PageResult;
import com.github.pagehelper.Page;
import com.movie.dao.MovieDao;
import com.movie.model.Movie;
import com.movie.service.MovieService;
import javax.annotation.Resource;
import com.util.PageFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p> MovieService </p>
 * @author:  jidn
 * @Date :   2019-12-20 16:49:50
 */
@Service("movieService")
public class MovieServiceImpl implements MovieService{

    @Resource
    private MovieDao movieDao;

    @Override
    public PageResult findPageBySql(Map<String,String> param) {
        Map<String,Object> paraMap = new HashMap<>(3);
        Page<Movie> page = PageFactory.getPage(param);
        String moviceName = param.get("moviceName");
        String director = param.get("director");
        String mainCharacter = param.get("mainCharacter");

        if(!StringUtils.isEmpty(moviceName)){
            paraMap.put("moviceName","%"+moviceName+"%");
        }
        if(!StringUtils.isEmpty(director)){
            paraMap.put("director","%"+director+"%");
        }
        if(!StringUtils.isEmpty(mainCharacter)){
            paraMap.put("mainCharacter","%"+mainCharacter+"%");
        }
        List<Movie> list = movieDao.findPageBySql(paraMap);
        return new PageResult(list,page.getTotal(), page.getPages(), page.getPageSize());
    }


    @Override
    public void crawlerPython() {
        try {
            String osName = System.getProperty("os.name");
            System.out.println("---------------------------------" + osName + "---------------------------------");
            if(osName.toLowerCase().indexOf("win") != -1){
                String path = this.getClass().getClassLoader().getResource("").getPath();
                String filePath = path + "/py/crawlerMovie.exe";
                System.out.println("--------------------------------- win ---------------------------------");
                String command = filePath.substring(1, filePath.length());

                System.out.println("------------------------ 执行命令：" + command + " ------------------------");

                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(command);

            }else if(osName.toLowerCase().indexOf("linux") != -1){
                String path = this.getClass().getClassLoader().getResource("").getPath();
                String filePath = path + "/py/crawlerMovie.py";
                System.out.println("--------------------------------- linux ---------------------------------");
                String command = "." + filePath  ;

                System.out.println("------------------------ 执行命令：" + command + " ------------------------");

                //exec1("chmod +x " + filePath); // chmod 777
                Runtime rt = Runtime.getRuntime();
                Process p = rt.exec(new String[]{"/bin/sh", "-c", "cd /; chmod +x "+filePath+";" + command});

            }else if(osName.toLowerCase().indexOf("mac") != -1){

            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
