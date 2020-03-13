package com.mvc.base.model;


import java.io.Serializable;
import java.util.Date;

/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/16 14:15
 * @Description:
 */
public class BaseModel implements Serializable {

    private Date created;

    private Date modified;


    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
