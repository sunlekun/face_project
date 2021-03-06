package com.demo.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Encoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 创建日期:2018年1月14日<br/>
 * 创建时间:下午6:50:53<br/>
 * 创建者 :yellowcong<br/>
 * 机能概要:用于Base64解码和编码
 */
public class Base64Utils {

    private static Base64Utils utils = null;

    private Base64Utils(){

    }

    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午7:23:30<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:单利 ，懒汉模式
     * @return
     */
    public static Base64Utils getInstance(){
        if(utils == null){
            synchronized (Base64Utils.class) {
                if(utils == null ){
                    utils = new Base64Utils();
                }
            }
        }
        return utils;
    }
    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午7:47:12<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:获取文件的大小
     * @param inFile 文件
     * @return 文件的大小
     */
    public int getFileSize(File inFile){
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            //文件长度
            int len = in.available();
            return len;
        }catch (Exception e) {
            // TODO: handle exception
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午6:57:53<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:将文件转化为base64
     * @return
     * @throws Exception 
     */
    public String file2Base64(File inFile){

        //将文件转化为字节码
        byte [] bytes = copyFile2Byte(inFile);
        if(bytes == null){
            return null;
        }

        //base64,将字节码转化为base64的字符串
        String result =  Base64.encodeBase64String(bytes);
       return result;
    }

    /**
     * 
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午7:09:02<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:将文件转化为字节码
     * @param inFile 
     * @return
     */
    private byte [] copyFile2Byte(File inFile){
        InputStream in = null;

        try {
            in = new FileInputStream(inFile);
            //文件长度
            int len = in.available();

            //定义数组
            byte [] bytes = new byte[len];

            //读取到数组里面
            in.read(bytes);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
            try {
                if(in != null){
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午6:54:02<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:将字符串转化为文件
     * @param strBase64 base64 编码的文件
     * @param outFile 输出的目标文件地址
     * @return 
     * @throws IOException
     */
    public static boolean base64ToFile(String strBase64,String puth,String name){
    	 File dir = new File(puth);
         if (!dir.exists()) {
             dir.mkdirs();
         }
        try {
        	File outFile = new File(puth+name);
            // 解码，然后将字节转换为文件
            byte[] bytes = Base64.decodeBase64(strBase64); // 将字符串转换为byte数组
            return copyByte2File(bytes,outFile);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            return false;
        }
    }
    /**
     * 创建日期:2018年1月14日<br/>
     * 创建时间:下午7:01:59<br/>
     * 创建用户:yellowcong<br/>
     * 机能概要:将字节码转化为文件
     * @param bytes
     * @param file
     */
    private static boolean copyByte2File(byte [] bytes,File file){
        FileOutputStream  out = null;
        try {
            //转化为输入流
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);

            //写出文件
            byte[] buffer = new byte[1024];

            out = new FileOutputStream(file);

            //写文件
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len); // 文件写操作
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
    
    
    /**
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     * @return
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    
    
    /**
     * 图片转base64字符串
     * @param imgFile 图片路径
     * @return
     */
    public static String imageToBase64Str(String imgFile) {
      InputStream inputStream = null;
      byte[] data = null;
      try {
        inputStream = new FileInputStream(imgFile);
        data = new byte[inputStream.available()];
        inputStream.read(data);
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      // 加密
      BASE64Encoder encoder = new BASE64Encoder();
      return encoder.encode(data);
    }

}
