package com.song.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mr.Song
 */
@Data
public class MergeResult implements Serializable {
    private int status;
    private Object data;
    private String msg;

    public static MergeResult ok(){
        MergeResult mergeResult = new MergeResult();
        mergeResult.setStatus(200);
        mergeResult.setMsg("OK");
        return mergeResult;
    }
    public static MergeResult ok(Object data){
        MergeResult mergeResult = new MergeResult();
        mergeResult.setStatus(200);
        mergeResult.setMsg("OK");
        mergeResult.setData(data);
        return mergeResult;
    }
    public static MergeResult error(String msg){
        MergeResult mergeResult =new MergeResult();
        mergeResult.setStatus(500);
        mergeResult.setMsg(msg);
        return mergeResult;
    }


}
