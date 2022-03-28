package com.zane.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/Hello")
public class HelloController {

    @RequestMapping(value = "/getPic")
    public String getPic(HttpServletResponse response, HttpServletRequest request){
        String picName = request.getParameter("pic");
        //拼接图片地址，这里调路径！！！！！！！！！
        String fileName = "C:\\Users\\Administrator\\Desktop\\";
        fileName+=picName+".jpg";

        try {
            //这里设置响应头
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //这里加载图片文件
        File file = new File(fileName);
        try {
            //59行这句加载图片
            FileInputStream fis = new FileInputStream(file);
            //60行这句输出图片给前端
            OutputStream os = response.getOutputStream();

            byte[] buff = new byte[1024];
            int n=0;
            while((n=fis.read(buff))!=-1){
                //如果while条件里面的fis读文件时读完了,就会返回-1表示读取完毕,
                //读到文件会表示一个byte字节,存到buff里面

                //以下两句将本地读到的图片数据交给前端
                os.write(buff,0,n);
                os.flush();
            }
            //关闭输入流
            fis.close();
            //关闭输出流
            os.close();
        } catch (Exception e) {
            System.out.println("文件无法读取");
            e.printStackTrace();
        }
        return null;
    }
}
