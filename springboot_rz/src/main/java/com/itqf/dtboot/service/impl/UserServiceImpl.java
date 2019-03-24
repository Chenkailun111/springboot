package com.itqf.dtboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itqf.dtboot.dao.SysUserMapper;
import com.itqf.dtboot.entity.SysUser;
import com.itqf.dtboot.entity.SysUserExample;
import com.itqf.dtboot.service.UserService;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.TableResult;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service(value = "userServiceImpl")
@Transactional
public class UserServiceImpl  implements UserService {

    //引用dao
    @Resource
    private SysUserMapper sysUserMapper;
    @Override
    public TableResult findUser(int offset, int limit, String search) {
        PageHelper.offsetPage(offset,limit);

        SysUserExample example = null;
        if (search!=null&&!"".equals(search)){
            example = new SysUserExample();
            SysUserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameLike("%"+search+"%");
        }

        List<SysUser> list = sysUserMapper.selectByExample(example);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);

        TableResult result = new TableResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(pageInfo.getList());

        return result;
    }

    @Override
    public R save(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUser.setStatus((byte)1);
        sysUser.setCreateUserId(1l);//创建人id

        sysUser.setPassword(new Md5Hash(sysUser.getPassword(),sysUser.getUsername(),1024).toString());
        int i = sysUserMapper.insertSelective(sysUser);

        return i>0?R.ok("新增成功"):R.error("新增失败");
    }

    @Override
    public R findById(long userId) {
        SysUser user = sysUserMapper.selectByPrimaryKey(userId);
        if (user!=null){
            return R.ok().put("user",user);
        }
        return R.error();
    }

    @Override
    public R update(SysUser sysUser) {
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        return i>0?R.ok("修改成功"):R.error();
    }

    @Override
    public R delete(List<Long> ids) {
        for (Long id : ids) {
            if (id==1){
                return R.error("管理员不能删除");
            }
        }

        SysUserExample example  = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdIn(ids);
        int i = sysUserMapper.deleteByExample(example);
        return i>0?R.ok():R.error();
    }

    @Override
    public SysUser findByUsername(String username) {

        List<SysUser> list = sysUserMapper.findByUsername(username);
        if (list!=null&&list.size()>0) {
            return list.get(0);
        }
        return null;
    }
}
