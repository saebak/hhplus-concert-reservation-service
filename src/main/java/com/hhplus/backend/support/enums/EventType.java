package com.hhplus.backend.support.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum EventType {
    INIT            (10,  "INIT"),
    RECEIVED        (20, "RECEIVED"),
    SUCCESS         (30, "SUCCESS")
    ;

    private int resultCode;
    private String resultMessage;


    EventType(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
    public int getResultCode() {
        return this.resultCode;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }

    private static final Map<Integer, EventType> LOOKUP = new HashMap<Integer, EventType>();

    static {
        for (EventType elem : EnumSet.allOf(EventType.class)) {
            LOOKUP.put(elem.getResultCode(), elem);
        }
    }


    public static EventType get(int code) {
        return LOOKUP.get(code);
    }

    public static int getResultCode(int code) {
        return get(code).resultCode;
    }

    public static String getResultMessage(int code) {
        return get(code).resultMessage;
    }

}
