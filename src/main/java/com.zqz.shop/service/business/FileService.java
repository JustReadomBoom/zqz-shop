package com.zqz.shop.service.business;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author: ZQZ
 * @Description:
 * @ClassName: FileService
 * @Date: Created in 13:45 2022-11-15
 */
@Service
@Slf4j
public class FileService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String keyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String secret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.out.endpoint}")
    private String outEndPoint;

    private static final String SLASH = "/";
    private static final String CONTENT_TYPE = "*/*";
    private static final String FOLDER = "zqz/shop/img";
    private static final String SUFFIX_IMG = ".jpg";
    private static final String HTTPS = "https://";


    /**
     * 文件上传
     * @param file
     * @param fileName
     * @return
     */
    public String upload(MultipartFile file, String fileName) {
        fileName = FOLDER + SLASH + fileName + SUFFIX_IMG;
        InputStream inputStream = null;
        OSS ossClient = null;
        try {
            //获取文件输入流
            inputStream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(CONTENT_TYPE);
            //创建OSSClient实例
            ossClient = new OSSClientBuilder().build(endpoint, keyId, secret);
            ossClient.putObject(bucketName, fileName, inputStream, metadata);
            return HTTPS + outEndPoint + SLASH + fileName;
        } catch (Exception e) {
            log.error("file upload error:{}", e.getMessage(), e);
            return null;
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
                if (null != ossClient) {
                    ossClient.shutdown();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
