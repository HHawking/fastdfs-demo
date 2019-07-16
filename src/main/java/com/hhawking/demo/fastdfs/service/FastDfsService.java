package com.hhawking.demo.fastdfs.service;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FastDfsService {

    public String upload(String filePath){

        //读取resources下的FastDfs配置文件
        ClassPathResource classPathResource = new ClassPathResource("fdfs_client.properties");

        try(InputStream inputStream = classPathResource.getInputStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            ClientGlobal.initByProperties(properties);
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer,null);
            String[] fileIds = storageClient.upload_file(filePath, "xls", null);
            return "http://" + properties.getProperty("host")+ "/" + fileIds[0] + "/" + fileIds[1];
        }catch (IOException | MyException e){
            e.printStackTrace();
            return null;
        }
    }
}
