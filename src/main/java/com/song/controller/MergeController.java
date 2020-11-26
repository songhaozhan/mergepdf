package com.song.controller;

import com.song.pojo.MergeResult;
import com.song.service.MergeService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Random;

/**
 * @author Mr.Song
 */
@RestController
public class MergeController {
    @Autowired
    private MergeService mergeService;
    //单文件上传
    @RequestMapping(value = "/transform",method = RequestMethod.POST)
    public MergeResult uploadFile(MultipartFile file,HttpSession session){
        String filePath = "F://mergepdf//"+"word"+System.currentTimeMillis() +"//";
        if (file != null && !file.isEmpty()) {
            try {
                File path = new File(filePath);
                if (!path.exists()){
                    path.mkdirs();
                }
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(
                                new File(filePath,file.getOriginalFilename())
                        )
                );
                stream.write(bytes);
                stream.close();
                //转word
                String pdfName = filePath+file.getOriginalFilename();
                PDDocument doc = PDDocument.load(new File(pdfName));
                int pageNum = doc.getNumberOfPages();
                String pathName = pdfName.substring(0,pdfName.lastIndexOf("."));
                String wordName = pathName+".doc";
                File file1 = new File(wordName);
                if (!file1.exists()){
                    file1.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file1);
                Writer writer = new OutputStreamWriter(fos,"UTF-8");
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setSortByPosition(true);//排序
                stripper.setStartPage(1);//设置转换的开始页
                stripper.setEndPage(pageNum);//设置转换的结束页
                stripper.writeText(doc, writer);
                writer.close();
                doc.close();
                System.out.println("转换完成");
                session.setAttribute("wordPath", filePath);
                wordName = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."))+".doc";
                session.setAttribute("wordName", wordName);
            } catch (Exception e) {
                e.printStackTrace();
                return MergeResult.error("出现异常，上传文件失败");
            }
        }else {
            return MergeResult.error("文件为空，上传文件失败");
        }
        return MergeResult.ok("上传成功");
    }
    //多文件上传
    @RequestMapping(value = "/batch/upload",method = RequestMethod.POST)
    public MergeResult batchUploadFile(MultipartFile[] files, HttpSession session){
        return mergeService.mergeFiles(files,session);
    }



}
