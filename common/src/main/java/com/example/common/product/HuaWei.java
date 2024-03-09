package com.example.common.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : BG547563
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HuaWei extends Phone{

    private Integer meta;


    public HuaWei(Integer meta) {
        this.meta = meta;
    }
}
