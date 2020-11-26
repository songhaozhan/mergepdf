package com.song.controller;

import com.song.pojo.MergeResult;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @author Mr.Song
 */
@Controller
public class DownLoadFileController {
    @RequestMapping("/download")
    public void downLoadNewFile(HttpServletResponse response, HttpSession session){
        String filePath = (String) session.getAttribute("filePath");
        if (filePath == null || filePath.isEmpty()){
            System.out.println("下载失败，文件不存在");
        }
        String fileName = "newFile.pdf";
        response.setHeader("Content-Disposition", "attachment;filename="+fileName);
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
