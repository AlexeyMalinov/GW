package ru.alexeymalinov.taskautomation.core.services.guirobotservice;

import java.awt.event.KeyEvent;

public enum KeyBoardKey {

    ENTER(KeyEvent.VK_ENTER);

    int code;

    KeyBoardKey(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }
}
