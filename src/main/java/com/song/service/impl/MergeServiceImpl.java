package com.song.service.impl;

import com.song.dao.MergeDao;
import com.song.pojo.MergeResult;
import com.song.service.MergeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Mr.Song
 */
@Service
public class MergeServiceImpl implements MergeService {
    @Autowired
    private MergeDao mergeDao;
    @Value("${pdf.upload.filePath}")
    private String basePath;
    @Override
    public MergeResult mergeFiles(MultipartFile[] files, HttpSession session) {
        //文件上传的本地文件夹
        basePath = basePath+ System.currentTimeMillis()+"\\";
        String[] fileNames = new String[files.length];
        if (!new File(basePath).exists()){
            new File(basePath).mkdirs();
        }
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.length; i++) {
            file = files[i];
            if(! file.isEmpty()){
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(
                            new FileOutputStream(
                                    new File(basePath,file.getOriginalFilename()
                                    )
                            )
                    );
                    stream.write(bytes);
                    stream.close();
                    //记录上传文件的名称
                    fileNames[i] = basePath+file.getOriginalFilename();
                }catch (Exception e){
                    e.printStackTrace();
                    stream = null;
                    return MergeResult.error("合并失败，有异常");
                }
            }else {
                return MergeResult.error("上传文件为空，合并失败");
            }
        }
        //合并上传的pdf文件为一个文件  新文件为newFile.pdf

        boolean b = mergeDao.mergePdfFiles(fileNames, basePath + "newFile.pdf");
        if (b){
            session.setAttribute("filePath", basePath);
            session.setAttribute("newName", "newFile.pdf");
        }
        return b?MergeResult.ok("合并成功"):MergeResult.error("合并失败");
    }

}
