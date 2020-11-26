package com.song.controller;

import com.song.pojo.MergeResult;
import com.song.service.MergeService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;

/**
 * @author Mr.Song
 */
@RestController
public class MergeController {
    @Autowired
    private MergeService mergeService;
    //单文件上传
    @RequestMapping(value = "/upload")
    public String uploadFile(MultipartFile file){
        String filePath = "F://mergepdf";
        if (file != null && !file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(filePath,file.getOriginalFilename())
                        )
                );
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "出现异常，上传文件失败";
            }
        }else {
            return "文件为空，上传文件失败";
        }
        return "上传成功";
    }
    //多文件上传
    @RequestMapping(value = "/batch/upload",method = RequestMethod.POST)
    public MergeResult batchUploadFile(MultipartFile[] files, HttpSession session){
        return mergeService.mergeFiles(files,session);
    }

}
