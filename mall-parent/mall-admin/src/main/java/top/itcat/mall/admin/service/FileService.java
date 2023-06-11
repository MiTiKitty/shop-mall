package top.itcat.mall.admin.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @className: FileService <br/>
 * @description: 文件服务接口 <br/>
 * @author: CatKitty 33641 <br/>
 * @date: 2023/06/11 <br/>
 * @version: 1.0.0 <br/>
 */
public interface FileService {

    /**
     * 文件上传
     *
     * @param file
     *         文件
     * @return 返回结果
     */
    Object upload(MultipartFile file);

    /**
     * 删除文件
     *
     * @param name
     *         文件名称
     * @return 返回结果
     */
    Object delete(String name);
}
