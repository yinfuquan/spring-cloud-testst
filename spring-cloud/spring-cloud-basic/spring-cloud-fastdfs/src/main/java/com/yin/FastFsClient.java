package com.yin;

import cn.hutool.core.io.FileUtil;
import com.github.tobato.fastdfs.domain.fdfs.FileInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;

/**
 * FastFs工具类
 */
@Component
public class FastFsClient {

    private static Logger logger = LoggerFactory.getLogger(FastFsClient.class);

    @Resource
    private FastFileStorageClient storageClient;

    /**
     * 上传文件
     *
     * @param file        文件
     * @param metaDataSet 文件元数据
     * @return 文件全路径
     */
    public String uploadFile(File file, Set<MetaData> metaDataSet) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = storageClient.uploadFile(inputStream, file.length(), FileUtil.extName(file.getName()), metaDataSet);
            return storePath.getFullPath();
        } catch (FileNotFoundException e) {
            logger.error("上传文件失败", e);
        }
        return null;
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件全路径
     */
    public String uploadFile(File file) {
        return uploadFile(file, null);
    }

    /**
     * 上传文件
     *
     * @param filePath 文件全路径
     * @return 文件全路径
     * @author YY
     * @date 2019/9/16 14:57
     */
    public String uploadFile(String filePath) {
        return uploadFile(new File(filePath));
    }


    /**
     * 上传文件
     *
     * @param bytes       文件的内容，字节数组
     * @param extName     文件扩展名
     * @param metaDataSet 文件元数据
     * @return 文件全路径
     */
    public String uploadFile(byte[] bytes, String extName, Set<MetaData> metaDataSet) {
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        StorePath storePath = storageClient.uploadFile(stream, bytes.length, extName, metaDataSet);
        return storePath.getFullPath();
    }

    /**
     * 上传文件
     *
     * @param bytes   文件的内容，字节数组
     * @param extName 文件扩展名
     * @return 文件全路径
     */
    public String uploadFile(byte[] bytes, String extName) {
        return uploadFile(bytes, extName, null);
    }

    /**
     * 上传文件（字符串生成文件）
     *
     * @param content 文件的内容
     * @param extName 文件扩展名
     * @return 文件全路径
     */
    public String uploadFile(String content, String extName) {
        return uploadFile(content.getBytes(StandardCharsets.UTF_8), extName);
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件全路径
     */
    public String uploadFile(MultipartFile file) {
        try {
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), FileUtil.extName(file.getOriginalFilename()), null);
            return storePath.getFullPath();
        } catch (IOException e) {
            logger.error("上传文件失败", e);
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param groupName 组名
     * @param filePath  文件相对路径 不包含组名 如M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return byte[] 文件字节数组
     */
    public byte[] downloadFileAsBytes(String groupName, String filePath) {
        try {
            return storageClient.downloadFile(groupName, filePath, new DownloadByteArray());
        } catch (Exception e) {
            logger.error("下载文件失败", e);
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param filePath 文件相对路径 包含组名 如group1/M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return byte[] 文件字节数组
     */
    public byte[] downloadFileAsBytes(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        return downloadFileAsBytes(storePath.getGroup(), storePath.getPath());
    }

    /**
     * 下载文件
     *
     * @param filePath 文件相对路径 包含组名 如group1/M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return byte[] 文件字节数组
     */
    public InputStream downloadFileAsStream(String filePath) {
        byte[] fileByte = downloadFileAsBytes(filePath);
        return fileByte == null ? null : new ByteArrayInputStream(fileByte);
    }

    /**
     * 删除文件
     *
     * @param groupName 组名
     * @param filePath  文件相对路径 不包含组名 如M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return FileInfo 文件信息
     */
    public boolean deleteFile(String groupName, String filePath) {
        try {
            storageClient.deleteFile(groupName, filePath);
            return true;
        } catch (Exception e) {
            logger.error("删除文件失败", e);
        }
        return false;
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径 如group1/M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return FileInfo 文件信息
     */
    public boolean deleteFile(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        return deleteFile(storePath.getGroup(), storePath.getPath());
    }

    /**
     * 获取文件信息
     *
     * @param groupName 组名
     * @param filePath  文件相对路径 不包含组名 如M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return FileInfo 文件信息
     */
    public FileInfo getFileInfo(String groupName, String filePath) {
        return storageClient.queryFileInfo(groupName, filePath);
    }

    /**
     * 获取文件信息
     *
     * @param filePath 文件相对路径 包含组名 如group1/M00/00/00/CgjKmV1_cDWAMOvLAA68PsTMRk4931.png
     * @return FileInfo 文件信息
     */
    public FileInfo getFileInfo(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        return getFileInfo(storePath.getGroup(), storePath.getPath());
    }
}
