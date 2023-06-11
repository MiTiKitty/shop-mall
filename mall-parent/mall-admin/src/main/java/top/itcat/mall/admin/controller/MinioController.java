package top.itcat.mall.admin.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.itcat.mall.admin.service.FileService;
import top.itcat.mall.common.api.CommonResult;

import javax.annotation.Resource;

/**
 * @className: MinioController <br/>
 * @description: Minio对象存储管理 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/11 <br/>
 * @version: 1.0.0 <br/>
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    @Resource(name = "minioFileService")
    private FileService fileService;

    /**
     * 上传文件
     *
     * @param file
     *         文件对象
     * @return 统一返回结果
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public CommonResult upload(@RequestPart("file") MultipartFile file) {
        Object result = fileService.upload(file);
        if (file == null) {
            return CommonResult.fail("文件上传失败");
        }
        return CommonResult.success(result);
    }

    /**
     * 根据文件对象名称删除文件
     *
     * @param objectName
     *         对象名称
     * @return 统一返回结果
     */
    @DeleteMapping("/del")
    public CommonResult remove(@RequestParam("objectName") String objectName) {
        Object result = fileService.delete(objectName);
        if (result == null) {
            return CommonResult.fail("文件删除失败");
        }
        return CommonResult.success(result);
    }
}
