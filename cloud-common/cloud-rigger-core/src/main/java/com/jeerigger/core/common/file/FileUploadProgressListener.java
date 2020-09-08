package com.jeerigger.core.common.file;

import org.apache.commons.fileupload.ProgressListener;
import javax.servlet.http.HttpSession;

/**
 * 文件上传进度监听器
 */
public class FileUploadProgressListener implements ProgressListener {

	private HttpSession session;

	public void setSession(HttpSession session){
		this.session=session;
		FileProgress progress=new FileProgress();
		session.setAttribute("FileProgress", progress);
	}

	@Override
	public void update(long bytesRead, long contentLength, int items) {
		FileProgress progress=(FileProgress)this.session.getAttribute("FileProgress");
		progress.setBytesRead(bytesRead);
		progress.setContentLength(contentLength);
		progress.setItems(items);
	}

}
