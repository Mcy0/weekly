package com.mcy.weekly;

import com.mcy.weekly.utils.MD5;
import com.mcy.weekly.utils.My;
import org.apache.ibatis.type.DateOnlyTypeHandler;
import org.apache.ibatis.type.StringTypeHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Logger log = LogManager.getLogger(Main.class);
        log.debug("aaa");
        log.info("bbb");
        log.error("ccc");
//        Thread.sleep(1000);
    }
}
