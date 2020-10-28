package com.github.dylanz666.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 * @author : dylanz
 * @since : 10/28/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class RedisZsetCache implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String value;
    private double score;
    private Set<String> zset;


    @Override
    public String toString() {
        return "{" +
                "\"key\":\"" + key + "\"," +
                "\"value\":\"" + value + "\"," +
                "\"score\":\"" + score + "\"," +
                "\"zset\":" + zset + "" +
                "}";
    }
}
