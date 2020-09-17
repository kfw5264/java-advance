package com.kangfawei.adapter;

/**
 * 类适配器模式（不推荐，继承增加耦合度）
 * @author kfw5264
 */
public class ClassAdapter extends TypeCPhone implements CommonInterface {
    @Override
    public void ListenMusicByCommonHeadset() {
        System.out.println("借助Type-c转3.5mm接口的适配器听歌");
        this.listenMusic();
    }
}
