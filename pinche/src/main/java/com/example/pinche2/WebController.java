package com.example.pinche2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.Response;

@RestController  
@RequestMapping("/web")  
public class WebController {  
	@Autowired
	private userMapper user;
    @Autowired
    private taskMapper task;
    @Autowired
    private static Cookie cookie;

    @RequestMapping(value = "/getwithcookies")
    public String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
            	System.out.println(cookie.getName());
            	System.out.println(cookie.getMaxAge());
                if(cookie.getName().equals("account")){
                    return cookie.getValue();
                }
            }
        }
		return null;
    }
    @RequestMapping(value = "/delectwithcookies")
    public static void delectCookie(HttpServletResponse response){
    	String cookieName;
    	String value;
        cookieName="account";
        value=null;
    	Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        System.out.println("cookie删除成功");
    }
    @RequestMapping("/newuser")
    public String newuser(String account,String password){
    	//System.out.println(account);
		List x=user.select2(account);
		if(x.size()==0)
    	{
	    	if(user.insert(account,password))
	    		return "注册成功";
	    	else
	    		return "注册失败";
    	}
    	else
    		return "该用户名已存在";
    }
    @RequestMapping("/entry")
    public String entry(HttpServletResponse response,String account,String password){  
		List x=user.select2(account);

		if(x.size()>0)
    	{
			List y=user.select3(account, password);
    		if(y.size()>0)
    		{
    			String cookieName;
    	    	String value;
    	    	cookieName="account";
    	        value=account;
    	    	Cookie cookie = new Cookie(cookieName,value);
    	        cookie.setPath("/");
    	        cookie.setMaxAge(3600);
    	        response.addCookie(cookie);
    	        return "登陆成功";
    		}		
    		else
    			return "密码错误";
    	}
    	else
    		return "用户名不存在";
    }
    @RequestMapping("/newtask")
    public String newtask(HttpServletRequest request, String gathertime,
    		String startpoint, String destination){  
    	int t=0;
    	String account=null;
    	Cookie[] cookies = request.getCookies();
    	System.out.println(cookies);
        if(cookies != null){
            for(Cookie cookie : cookies){
            	System.out.println(cookie.getName());
            	System.out.println(cookie.getMaxAge());
                if(cookie.getName().equals("account")){
                    account=cookie.getValue();
                    t=1;
                }
            }
        }
        if(t!=1)
        	return "未登录";
    	if(task.insert(gathertime, account, startpoint, destination,1))
    		return "创建成功";
    	else
    		return "创建失败";
    }
    @RequestMapping("/showtask")
    public List<task> showtask(){  
    	return task.select1();
    }
    @RequestMapping("/selecttask")
    public List<task> selecttask(String gathertime,
    		String startpoint, String destination){
    	if(gathertime=="")
    	{
        	if(startpoint=="")
        	{
            	if(destination=="")
            	{	//一个参数都没有
            		return task.select1();
            	}
            	else
            	{	//只有destination
            		return task.select5(destination);
            	}
        	}
        	else
        	{
        		if(destination=="")
            	{	//只有startpoint
        			return task.select4(startpoint);
            	}
        		else
        		{	//只有startpoint，destination
        			return task.select8(startpoint,destination);
        		}
        	}
    	}
    	else
    	{
        	if(startpoint=="")
        	{
            	if(destination=="")
            	{	//只有gathertime
            		return task.select3(gathertime);
            	}
            	else
            	{	//只有gathertime，destination
            		return task.select7(gathertime,destination);
            	}
        	}
        	else
        	{
        		if(destination=="")
            	{	//只有gathertime，startpoint
        			return task.select6(gathertime,startpoint);
            	}
        		else
        		{	//只有gathertime，startpoint，destination
        			return task.select2(gathertime, startpoint, destination);
        		}
        	}
    	}
    }
    @RequestMapping("/inserttask")
    public String inserttask(HttpServletRequest request, int identification) {
    	int t=0;
    	String account=null;
    	Cookie[] cookies = request.getCookies();
    	System.out.println(cookies);
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("account")){
                	t=1;
                    account=cookie.getValue();
                }
            }
        }
        if(t!=1)
        	return "未登录";
    	List<task> x=task.select9(identification);
    	List y=user.select2(account);
    	if(x.get(0).getNumber()<4)
    	{
    		task.update1(identification);
	    	if(x.get(0).getNumber()==1)
	    	{
	    		task.update2(identification,account);
	    		return "加入成功";
	    	}	
	    	else if(x.get(0).getNumber()==2)
	    	{
	    		task.update3(identification,account);
	    		return "加入成功";
	    	}
	    	else if(x.get(0).getNumber()==3)
	    	{
	    		task.update4(identification,account);
	    		return "加入成功";
	    	}
	    	else
	    	{
	    		//java需要这种明知道不可能发生的else的return，即要求形式上不管什么情况都要有return，而不会去考虑实际情况，这种要怎么处理？
	    		return "加入失败";
	    	}
    	}
    	else
    		return "人数已满";
    }
    @RequestMapping("/deletetask")
    public String deletetask(HttpServletRequest request, int identification) {
    	int t=0;
    	String account=null;
    	Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("account")){
                    account=cookie.getValue();
                    t=1;
                }
            }
        }
        if(t!=1)
        	return "未登录";
    	List<task> x=task.select9(identification);
    	String a1=x.get(0).getAccount1();
    	String a2=x.get(0).getAccount2();
    	String a3=x.get(0).getAccount3();
    	String a4=x.get(0).getAccount4();
    	task.update6(identification);
    	if(x.get(0).getNumber()==0)
    	{
    		task.delete(identification);
    	}
    	else
    	{
	    	if(a4==account)
	    		task.update4(identification,null);
	    	else if(a3==account)
	    		task.update4(identification,a4);
	    	else if(a2==account)
	    	{
	    		task.update2(identification,a3);
	    		task.update3(identification,a4);
	    	}
	    	else if(a1==account)
	    	{
	    		task.update5(identification,a2);
	    		task.update2(identification,a3);
	    		task.update3(identification,a4);
	    	}
    	}
    	return "退出成功";
    }
}