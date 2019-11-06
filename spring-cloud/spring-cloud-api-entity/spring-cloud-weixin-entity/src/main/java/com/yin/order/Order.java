package com.yin.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private String message_id;

}