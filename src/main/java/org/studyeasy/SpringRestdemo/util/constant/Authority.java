package org.studyeasy.SpringRestdemo.util.constant;

public enum Authority {

    READ,
    WRITE,
    UPDATE,
    USER, // CAN update, read , delete self object
    ADMIN // can update any object
    
}
