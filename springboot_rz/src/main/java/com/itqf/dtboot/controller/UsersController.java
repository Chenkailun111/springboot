package com.itqf.dtboot.controller;

import com.google.code.kaptcha.Producer;
import com.itqf.dtboot.dao.SysUserMapper;
import com.itqf.dtboot.dto.SysUserDTO;
import com.itqf.dtboot.entity.SysUser;
import com.itqf.dtboot.entity.SysUserExample;
import com.itqf.dtboot.log.MyLog;
import com.itqf.dtboot.service.UserService;
import com.itqf.dtboot.utils.R;
import com.itqf.dtboot.utils.ShiroUtils;
import com.itqf.dtboot.utils.SysConstant;
import com.itqf.dtboot.utils.TableResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//@Controller
@RestController
public class UsersController {


   @Resource
    private UserService userService;
   @Resource
   private Producer producer;

//    @RequestMapping("/find")
//    public SysUser  findUser(){
//
//
//        //return   sysUserMapper.selectByPrimaryKey(1l);
//
//       // sysUserMapper.selectByExample(); 按照条件查询
//      //  sysUserMapper.countByExample(); 按照条件统计 count(*)
//        // /sysUserMapper.updateByExample();按照条件修改
//        //sysUserMapper.updateByExampleSelective();按照条件修改不为空的字段值
//      //  sysUserMapper.deleteByExample()根据条件删除
//
//        //封装查询条件
//        SysUserExample example = new SysUserExample();
//        SysUserExample.Criteria criteria =  example.createCriteria();
//
//        //andXXXBetween()  where  xxx between ? and ?;
//        //andXXXEqualTo()  where xxx=?;
//        //andXXXGreaterThan  where xxx>?;
//        //andXXXLessThan()  where xxx  <?;
//        //andCreateTimeGreaterThanOrEqualTo()  where xxx >=?;
//        //andCreateTimeLessThanOrEqualTo()  where xxx <=?;
//        //andCreateTimeIn() where xxx in (?,?,?);
//        //andCreateTimeIsNotNull()  where xxx is not null;
//        //andCreateTimeIsNull where xxx is null;
//        //andUsernameLike()  where xxx like ?
//        //andUsernameNotLike() where xxx not like ?
//
//        criteria.andCreateTimeGreaterThanOrEqualTo(Date.valueOf("2010-02-01"));
//        String name="a";
//        criteria.andUsernameLike("%"+name+"%");
//
//        List<Long> ids = new ArrayList<>();
//        ids.add(1l);
//        ids.add(2l);
//        criteria.andUserIdIn(ids);
//        // where createtime >=? and name like ? and id in (?,?);
//        List<SysUser> list = sysUserMapper.selectByExample(example);
//
//
//
//        System.out.println(list);
//
//        return list.get(0);
//    }

    @MyLog("生成验证码")
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws  Exception{

       String text =  producer.createText();
       System.out.println("验证码--->"+text);
       //存储到域对象中
        ShiroUtils.setAttribute(SysConstant.CODE_KEY,text);

       //存储验证码
       BufferedImage image = producer.createImage(text);
       ImageIO.write(image,"jpg",response.getOutputStream());
    }

    @MyLog("登录")
    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDTO user){//前端传递的json字符串  后端接收？
        System.out.println("----->"+user);

        //得到系统生成的验证码
        String text = (String) ShiroUtils.getAttribute(SysConstant.CODE_KEY);
        if (!text.equalsIgnoreCase(user.getCaptcha())){
            return R.error("验证码不正确");
        }

        Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getUsername(),1024);
        user.setPassword(md5Hash.toString());

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        //d得到subject对象
        Subject subject = SecurityUtils.getSubject();

        //登录
        try {
            //记住我功能
            if (user.isRememberMe()){
                token.setRememberMe(true);
            }
            subject.login(token);//调用自定义Realm进行认证和授权
            System.out.println(subject.isPermitted("sys:user:select"));
            System.out.println(subject.isPermitted("sys:role:select"));

            return  R.ok();
        } catch (Exception e) {
          return R.error(e.getMessage());
        }
    }

    @MyLog("查看用户列表")
     @RequiresPermissions("sys:user:list")
    //@RequiresPermissions(value = {"elevator:view", "onlineMonitoring:view"}, logical = Logical.OR)
    @RequestMapping("/sys/user/list")
    public TableResult findUser(int offset ,int limit,String search){
        return userService.findUser(offset,limit,search);
    }
    @MyLog("新增用户")
    @RequiresPermissions("sys:user:save")
    @RequestMapping("sys/user/save")
    public R save(@RequestBody SysUser sysUser){
        return userService.save(sysUser);
    }

    /**
     * 修改
     * @param userId
     * @return
     */
    @MyLog("查看用户")
    @RequiresPermissions("sys:user:info")
    @RequestMapping("/sys/user/info/{userId}")
    public R selectUser(@PathVariable  long userId){

        return userService.findById(userId);
    }



    @MyLog("修改用户")
    @RequiresPermissions("sys:user:update")
    @RequestMapping("sys/user/update")
    public R update(@RequestBody SysUser sysUser) {
        return  userService.update(sysUser);
    }
    //修改结束

    @MyLog("删除用户")
    @RequiresPermissions("sys:user:del")
    @RequestMapping("/sys/user/del")
    public R delete(@RequestBody List<Long> ids){

        return userService.delete(ids);
    }

    @MyLog("退出")
    @RequestMapping("/logout")
    public  void logout(){

        ShiroUtils.logout();

       // return "/login.html";
    }

    @RequestMapping("sys/user/info")
    public R userInfo(){
        SysUser sysUser  = ShiroUtils.getCurrentUser();
        return R.ok().put("user",sysUser);
    }

}
