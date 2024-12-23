package com.py;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @Author py
 * @Date 2024/11/26
 */
@Builder
@Data
@ToString
public class User {
    private Integer id;
    private String name;
    private Integer age;
}
