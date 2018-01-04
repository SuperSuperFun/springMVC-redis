package com.wangli.utils;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * @author wangli
 */
public class SerializeUtil {
    private static final Logger logger = Logger.getLogger(SerializeUtil.class);

    public static byte[] serialize(Object object) {
        byte[] bytes = null;
        if (object != null) {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                bytes = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("序列化出现异常：" + e);
            } finally {
                close(oos);
                close(baos);
            }
        }
        return bytes;
    }

    public static Object unserialize(byte[] bytes) {
        Object returnObject = new Object();

        if (bytes != null) {
            ByteArrayInputStream bais = null;
            ObjectInputStream bis = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                bis = new ObjectInputStream(bais);
                returnObject = bis.readObject();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("反序列化出现异常：" + e);
            } finally {
                close(bis);
                close(bais);
            }
        } else {
            returnObject = null;
        }
        return returnObject;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("关闭io流出现异常：" + e);
            }
        }
    }
}
