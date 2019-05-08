package com.hangyi.eyunda.chat;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.hangyi.eyunda.data.chat.UploadFileStatus;

@Component
public class UploadFileListener implements ProgressListener {
    private HttpSession session;
 
    public UploadFileListener() { 
    	
    } 
    
    public void setSession(HttpSession session){
        this.session=session;
        UploadFileStatus status = new UploadFileStatus();
        session.setAttribute("status", status);
    }
 
    /*
     * pBytesRead 到目前为止读取文件的比特数 pContentLength 文件总大小 pItems 目前正在读取第几个文件
     */
    public void update(long pBytesRead, long pContentLength, int pItems) {
    	UploadFileStatus status = (UploadFileStatus) session.getAttribute("status");
        status.setPBytesRead(pBytesRead);
        status.setPContentLength(pContentLength);
        status.setPItems(pItems);
    	session.setAttribute("upload_ps", status);
    }
 
}
