package com.kangfawei.adapter;

import org.junit.Test;

public class App {

    @Test
    public void testClassAdapter(){
        ClassAdapter adapter = new ClassAdapter();
        adapter.ListenMusicByCommonHeadset();
    }

    @Test
    public void testObjectAdapter(){
        TypeCPhone phone = new TypeCPhone();
        ObjectAdapter adapter = new ObjectAdapter(phone);
        adapter.ListenMusicByCommonHeadset();
    }
}
