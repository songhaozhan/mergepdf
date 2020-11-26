package com.song.service;

import com.song.pojo.MergeResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

/**
 * @author Mr.Song
 */
public interface MergeService {
    MergeResult mergeFiles(MultipartFile[] files, HttpSession session);

}
