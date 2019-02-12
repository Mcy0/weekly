package com.mcy.weekly.service.impl;

import com.mcy.weekly.service.FileService;
import com.mcy.weekly.utils.My;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {

    private Logger log = LogManager.getLogger(FileServiceImpl.class);


    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return true or false
     */
    @Override
    public Boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println();
            log.warn("删除文件失败:" + path + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return file.delete();
        }
        return false;
    }

    @Override
    public String addPictureFile(HttpServletRequest request, Date date, Map<String, Object> map) {
        String picture = null;
        MultipartHttpServletRequest mhsr = (MultipartHttpServletRequest) request;
        MultipartFile file = mhsr.getFile("file");
        if (file != null){
            String str = file.getOriginalFilename();
            if (str.indexOf(".jpg") < 0 && str.indexOf(".jpeg") < 0 && str.indexOf(".png") < 0 && str.indexOf(".gif") < 0){
                map.put("success", false);
                map.put("msg", "照片格式不支持");
                return null;
            }
            int index = str.indexOf(".");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String fileName = simpleDateFormat.format(date) + str.substring(index);
            File dest = new File(My.getMeetingPicture() + fileName);
            Path path = dest.toPath();
            try{
                file.transferTo(path);
                picture = My.getLocal() + "meeting/picture.do?picture=" + My.getMeetingPicture() + fileName;
            } catch (IOException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                map.put("success", false);
                map.put("msg", "上传文件失败");
                return null;
            }
        }
        if (picture != null && !"".equals(picture)){
            return picture;
        }
        return "false";
    }
}
