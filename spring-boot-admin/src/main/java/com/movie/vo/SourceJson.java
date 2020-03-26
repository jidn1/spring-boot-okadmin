package com.movie.vo;

import lombok.Data;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/30 19:20
 * @Description:
 */
@Data
public class SourceJson {

    private Integer id;

    private String name;

    private String url;

    private String httpApi;

    private String httpsApi;

    private String type;
}
