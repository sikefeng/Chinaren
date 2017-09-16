package com.sikefeng.chinaren.utils;


final class QuitCockroachException extends RuntimeException {
    /**
     * 构造函数
     * @param message String
     */
    QuitCockroachException(String message) {
        super(message);
    }
}
