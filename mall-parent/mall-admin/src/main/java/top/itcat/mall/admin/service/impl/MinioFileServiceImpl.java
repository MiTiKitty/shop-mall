package top.itcat.mall.admin.service.impl;

import cn.hutool.json.JSONUtil;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.itcat.mall.admin.service.FileService;
import top.itcat.mall.admin.vo.MinioFileVO;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @className: MinioFileServiceImpl <br/>
 * @description: Minio对象存储服务实现类 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/11 <br/>
 * @version: 1.0.0 <br/>
 */
@Slf4j
@Service("minioFileService")
public class MinioFileServiceImpl implements FileService {

    @Value("${minio.baseUrl}")
    private String baseUrl;

    @Value("${minio.endpoint}")
    private String ENDPOINT;

    @Value("${minio.bucketName}")
    private String BUCKET_NAME;

    @Value("${minio.accessKey}")
    private String ACCESS_KEY;

    @Value("${minio.secretKey}")
    private String SECRET_KEY;

    @Override
    public Object upload(MultipartFile file) {
        InputStream in = null;
        try {
            in = file.getInputStream();
            //创建一个MinIO的Java客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build());
            if (!isExist) {
                return null;
            }
            String filename = file.getOriginalFilename();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            // 设置存储对象名称
            String objectName = sdf.format(new Date()) + "/" + filename;
            // 使用putObject上传一个文件到存储桶中
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(BUCKET_NAME)
                    .object(objectName)
                    .contentType(file.getContentType())
                    .stream(in, file.getSize(), ObjectWriteArgs.MIN_MULTIPART_SIZE).build();
            minioClient.putObject(putObjectArgs);
            log.info("文件上传成功!");
            MinioFileVO vo = new MinioFileVO();
            vo.setName(filename);
            vo.setUrl(baseUrl + "/" + BUCKET_NAME + "/" + objectName);
            return vo;
        } catch (IOException | XmlParserException | InternalException | ErrorResponseException | InvalidResponseException | InvalidKeyException | InsufficientDataException | NoSuchAlgorithmException | ServerException e) {
            e.printStackTrace();
            log.info("上传发生错误: {}！", e.getMessage());
        } finally {
            try {
                // 关闭流
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Object delete(String name) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(ENDPOINT)
                    .credentials(ACCESS_KEY, SECRET_KEY)
                    .build();
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(BUCKET_NAME).object(name).build());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
