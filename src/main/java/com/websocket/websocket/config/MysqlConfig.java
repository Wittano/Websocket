package com.websocket.websocket.config;

import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MysqlConfig extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8_polish_ci";
    }
}
