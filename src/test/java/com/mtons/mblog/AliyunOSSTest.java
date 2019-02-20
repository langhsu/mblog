package com.mtons.mblog;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.mtons.mblog.base.utils.ImageUtils;
import com.upyun.UpYunUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * created by langhsu
 * on 2019/1/22
 */
public class AliyunOSSTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "mtons";

        File file = new File("F:/data/a_2.jpg");
        byte[] bytes = ImageUtils.screenshot(file, 360, 200);
        String key = UpYunUtils.md5(bytes);

        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
        PutObjectResult result = ossClient.putObject(bucketName, "static/"+key + ".jpg", new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
