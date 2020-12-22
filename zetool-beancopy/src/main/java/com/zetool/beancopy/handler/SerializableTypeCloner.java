package com.zetool.beancopy.handler;

import java.io.*;

public class SerializableTypeCloner implements TypeCloner {

    @Override
    public <T> T cloneValue(T t) {
        if(t == null) return null;
        return (T) copyFromWithIO((Serializable)t);
    }

    /**
     * 拷贝对象A和B同类型，并且实现了Serializable接口，就直接深度拷贝
     * @param <T>
     * @param <T>
     * @param sourceObj
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <T extends Serializable> T copyFromWithIO(T sourceObj){
        T bInstance = null;
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream(100);
        ObjectOutputStream oOs = null;
        try {
            oOs = new ObjectOutputStream(bAOS);// 将对象写入字节缓存区
            oOs.writeObject(sourceObj); // 写入对象
            oOs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream oIS = null;
        try {
            oIS = new ObjectInputStream(new ByteArrayInputStream(bAOS.toByteArray()));
            bInstance = (T) oIS.readObject();// 读取对象
            oIS.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bInstance;
    }

}
