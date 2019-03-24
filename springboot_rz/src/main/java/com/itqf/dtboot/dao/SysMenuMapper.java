package com.itqf.dtboot.dao;

import com.itqf.dtboot.entity.SysMenu;
import com.itqf.dtboot.entity.SysMenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysMenuMapper {
    int countByExample(SysMenuExample example);

    int deleteByExample(SysMenuExample example);

    int deleteByPrimaryKey(Long menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    List<SysMenu> selectByExample(SysMenuExample example);

    SysMenu selectByPrimaryKey(Long menuId);

    int updateByExampleSelective(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByExample(@Param("record") SysMenu record, @Param("example") SysMenuExample example);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);

    /**
     * type:0  目录
     * type:1  菜单
     * type:2  按钮
     * @return
     */
    List<SysMenu>  findMenuNotButton();

    /**
     *查询用户的权限
     */
    List<String> findPermsByUserId(Long userId);

    /**
     * 查询目录的
     * @param userId
     * @return
     */
    List<Map<String,Object>>  findParentMenuByUserId(long userId);

    //查询菜单

    /**
     * @Param("userId")  注解，底层能够把参数转化为map,其中键为注解的value属性的值，
     * 等价于：Map map = new HashMap();
     * map.put("userId",1);
     * map.put("parentId",2);
     * @param userId
     * @param parentId
     * @return
     */
    List<Map<String,Object>> findMenuByUserId(@Param("userId") long userId,@Param("parentId") long parentId);

}