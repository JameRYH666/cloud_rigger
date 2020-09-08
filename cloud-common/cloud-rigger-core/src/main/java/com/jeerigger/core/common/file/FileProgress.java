package com.jeerigger.core.common.file;

import lombok.Data;

import java.io.Serializable;

@Data
public class FileProgress implements Serializable {
    private long bytesRead; //已读取文件的比特数
    private long contentLength;//文件总比特数
    private long items; //正读的第几个文件
}
