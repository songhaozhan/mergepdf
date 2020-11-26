package com.song.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author Mr.Song
 */
@Controller
public class DownLoadFileController {
    @RequestMapping("/download")
    public void downLoadNewFile(HttpServletResponse response, HttpSession session,int downType){
        String filePath = null;
        String fileName = null;
        if (downType==1) {
            filePath = (String) session.getAttribute("filePath");
            fileName = (String) session.getAttribute("newName");
        }else if (downType == 2){
            filePath = (String) session.getAttribute("wordPath");
            fileName = (String) session.getAttribute("wordName");
        }
        if (filePath == null || filePath.isEmpty()){
            System.out.println("下载失败，文件不存在");
        }
        /*try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        try {
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            //获取要下载的文件字节数组
            byte[] bytes = FileUtils.readFileToByteArray(new File(filePath,fileName));
            //获取输出流对象
            ServletOutputStream outputStream = response.getOutputStream();
            //将资源响应给浏览器
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
