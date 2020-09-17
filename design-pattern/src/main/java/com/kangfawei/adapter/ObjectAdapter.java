package com.kangfawei.adapter;

/**
 * 对象适配器
 * @author kfw5264
 */
public class ObjectAdapter implements CommonInterface {

    private TypeCPhone phone;

    public ObjectAdapter(TypeCPhone phone){
        this.phone = phone;
    }

    @Override
    public void ListenMusicByCommonHeadset() {
        System.out.println("借助Type-c转3.5mm接口的适配器听歌");
        phone.listenMusic();
    }
}
