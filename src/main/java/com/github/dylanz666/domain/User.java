package com.github.dylanz666.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author : dylanz
 * @since : 10/31/2020
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Component
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;
    private String roleName;

    @Override
    public String toString() {
        return "{" +
                "\"userName\":\"" + userName + "\"," +
                "\"roleName\":" + roleName + "" +
                "}";
    }
}