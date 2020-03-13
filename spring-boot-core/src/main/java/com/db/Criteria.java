package com.db;

import com.exception.CusException;
import com.mvc.base.dao.BaseDao;
import com.util.ReflectUtil;
import com.util.SpringUtil;
import tk.mybatis.mapper.entity.Example;
import java.io.Serializable;
import com.auth.AuthCheckUtil;
import java.util.List;


/**
 * @Copyright © 正经吉
 * @Author: Jidn
 * @Date: 2019/12/17 19:29
 * @Description: Criteria 封装的一个crud综合器  使用 必须继承 BaseDao
 */
public class Criteria<T extends Serializable, PK extends Serializable> {

    private BaseDao<T, PK> dao;

    private Class<T> clazz;

    private Example example = null;

    private Example.Criteria criteria = null;

    public Criteria() {
        this.dao = (BaseDao) SpringUtil.getBean(toLowerCaseFirstOne());
        //初始化example对象
        this.example = new Example(clazz);
        //创建查询条件对象
        this.criteria = example.createCriteria();
    }

    public Criteria(Class<T> clazz) {
        this.clazz = clazz;
        this.dao = (BaseDao) SpringUtil.getBean(toLowerCaseFirstOne());
        //初始化example对象
        this.example = new Example(clazz);
        //创建查询条件对象
        this.criteria = example.createCriteria();
    }

    /**
     * 添加
     *
     * @param t
     */
    public void save(T t) {
        ReflectUtil.save(t);
        this.dao.insertSelective(t);
    }

    /**
     * 修改
     *
     * @param t
     */
    public void update(T t) {
        ReflectUtil.update(t);
        this.dao.updateByPrimaryKeySelective(t);
    }

    /**
     * 删除 根据 主键
     *
     * @param pk
     * @return
     */
    public boolean deleteByPrimary(PK pk) {
        try {
            this.dao.deleteByPrimaryKey(pk);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除根据条件
     *
     * @return
     */
    public boolean deleteByExample() {
        try {
            this.dao.deleteByExample(example);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查询全部
     *
     * @return
     */
    public List<T> findAll() {
        return this.dao.selectAll();
    }

    /**
     * 查询数量
     *
     * @param t
     * @return
     */
    public Long count(T t) {
        int count = this.dao.selectCount(t);
        return Integer.valueOf(count).longValue();
    }

    /**
     * 根据条件查询数量
     * @return
     */
    public Long countByExample() {
        int count = this.dao.selectCountByExample(example);
        return Integer.valueOf(count).longValue();
    }

    /**
     * 根据条件查询
     *
     * @return
     */
    public T findByExample() {
        try {
            List<T> list = this.dao.selectByExample(example);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 根据条件查询 List
     * @return
     */
    public List<T> findListByExample() {
        return this.dao.selectByExample(example);
    }

    /**
     * @param condition
     * @param value
     */
    public void addFilter(String condition, Object value) {
        addCriteria(condition, value);
    }

    /**
     * 添加条件
     *
     * @param condition
     * @param value
     * @return
     */
    private Example.Criteria addCriteria(String condition, Object value) {
        /*-------------------------------------notlike处理-------------------------------------------------*/
        if (condition.contains("_notlike") || condition.contains("_NOTLIKE")) {
            String[] split = condition.split("_");
            this.criteria.andNotLike(split[0], value + "");
        } else if (condition.contains("_like") || condition.contains("_LIKE")) {
            /*-------------------------------------like处理-------------------------------------------------*/
            String[] split = condition.split("_");
            this.criteria.andLike(split[0], value + "");
        } else if (condition.contains("_in") || condition.contains("_IN")) {
            /*-------------------------------------in处理-------------------------------------------------*/
            String[] split = condition.split("_");

            if (value instanceof String) {
                String v = ((String) value).trim();
                String[] vArr = v.split(",");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < vArr.length; i++) {
                    sb.append("'" + vArr[i] + "'");
                    if (i < vArr.length - 1) {
                        sb.append(",");
                    }
                }
                this.criteria.andCondition(" " + split[0] + " " + "in" + " ( " + sb.toString() + " )");
            } else {
                this.criteria.andIn(split[0], (List) value);
            }

        } else if (condition.contains("_notin") || condition.contains("_NOTIN")) {
            /*-------------------------------------not in处理-------------------------------------------------*/
            String[] split = condition.split("_");
            if (value instanceof String) {
                String v = ((String) value).trim();
                String[] vArr = v.split(",");
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < vArr.length; i++) {
                    sb.append("'" + vArr[i] + "'");
                    if (i < vArr.length - 1) {
                        sb.append(",");
                    }
                }
                this.criteria.andCondition(" " + split[0] + " " + "not in" + " ( " + sb.toString() + " )");
            } else {
                this.criteria.andNotIn(split[0], (List) value);
            }
        } else if (condition.contains("_isnull") || condition.contains("_ISNULL")) {
            /*-------------------------------------is null处理-------------------------------------------------*/
            String[] split = condition.split("_");
            this.criteria.andIsNull(split[0]);
        } else if (condition.contains("_isnotnull") || condition.contains("_ISNOTNULL")) {
            /*-------------------------------------is not null处理-------------------------------------------------*/
            String[] split = condition.split("_");
            this.criteria.andIsNotNull(split[0]);
        }
        /*-------------------------------------【_EQ】【_GT】【_LT】【_NEQ】处理-------------------------------------------------*/
        /**
         *        【_EQ】           =
         *        【_GT】           >
         *        【_LT】           <
         *        【_NEQ】          !=
         *        【_GEQ】          >=
         *        【_LEQ】          <=
         */
        else if (condition.contains("_EQ")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + "=", value);
        } else if (condition.contains("_GT")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + ">", value);
        } else if (condition.contains("_LT")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + "<", value);
        } else if (condition.contains("_NEQ")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + "!=", value);
        } else if (condition.contains("_GET")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + ">=", value);
        } else if (condition.contains("_LET")) {
            String[] strArr = condition.split("_");
            this.criteria.andCondition(strArr[0] + "<=", value);
        } else {
            /*-------------------------------------【=】【>】【<】【!=】【>=】【<=】处理-------------------------------------------------*/
            this.criteria.andCondition(condition, value);
        }
        return this.criteria;
    }


    public String toLowerCaseFirstOne() {
        if(this.clazz == null){
            throw new CusException("Class name must not be empty");
        }
        String s = this.clazz.getSimpleName();
        if (Character.isLowerCase(s.charAt(0))) {
            return s + "Dao";
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString() + "Dao";
        }
    }
}
