package com.example.satoken.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestDto extends BaseDto implements Serializable {
    private String userName;
}
