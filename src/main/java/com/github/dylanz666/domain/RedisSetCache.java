package com.github.dylanz666.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author : dylanz
 * @since : 10/28/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class RedisSetCache implements Serializable {
    private static final long serialVersionUID = 1L;

    private String key;
    private String[] sets;

    @Override
    public String toString() {
        return "{" +
                "\"key\":\"" + key + "\"," +
                "\"sets\":" + Arrays.toString(sets) + "" +
                "}";
    }
}
