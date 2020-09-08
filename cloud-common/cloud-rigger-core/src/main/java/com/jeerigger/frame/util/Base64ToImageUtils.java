package com.jeerigger.frame.util;


import com.jeerigger.frame.exception.FileException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @projectName: dap
 * @packageName: PACKAGE_NAME
 * @description:
 * @author: huayang.bai
 * @date: 2020/3/10 14:48
 */
public class Base64ToImageUtils {


    private Base64ToImageUtils() {
        throw new RuntimeException(this.getClass().getName() + "是工具类，不允许实例化对象！");
    }

    public static String generatorImage(String data, String imagePath) {
        return generatorImage(data, imagePath, 1.0D);
    }

    /**
     * @param data
     * @param imagePath
     * @param ratio     放大比例：1.0不是放大
     * @return
     */
    public static String generatorImage(String data, String imagePath, double ratio) {
        String contentType = getContentType(data);
        String imageType = getImageType(contentType);
        // 图片输出路径
        String fileName = UUID.randomUUID().toString();
        generator(data, imagePath, fileName, contentType, imageType, ratio);
        return fileName + "." + imageType;
    }


    private static void generator(String data, String imagePath, String fileName, String contentType,
                                  String imageType, double ratio) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                boolean exists = imageFile.mkdirs();
                if (!exists)
                    throw new FileException("文件未建立");
            }
            // base64编码解码
            byte[] k = Base64.decode(data.substring(contentType.length()));
            InputStream is = new ByteArrayInputStream(k);
            String imgFilePath = imagePath + File.separator + fileName + "." + imageType;
            BufferedImage image = ImageIO.read(is);
            // 设置图片是否缩放
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);
            Image newimage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(newimage, 0, 0, null);
            g.dispose();
            // 使用io将图片写入文件中
            ImageIO.write(tag, imageType, new File(imgFilePath));
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    private static String getContentType(String data) {
        String contentType = null;
        if (data != null && !data.equals("")) {
            contentType = data.substring(0, data.indexOf(",") + 1);
        }
        return contentType;
    }

    private static String getImageType(String contentType) {
        String imageType = null;
        if (contentType != null && !contentType.equals("")) {
            String regix = "image/";
            imageType = contentType.substring(contentType.indexOf(regix) + regix.length(), contentType.indexOf(";"
            ));
        }
        return imageType;
    }

}
