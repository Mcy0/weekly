package com.mcy.weekly.service;

import com.mcy.weekly.pojo.Meeting;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

public interface FileService {
    Boolean deleteFile(String path);
    String addPictureFile(HttpServletRequest request, Date date, Map<String, Object> map);
}
