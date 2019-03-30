package com.biubiu.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Haibiao.Zhang on 2019-03-30 16:21
 */
@Data
@Builder
public class Message {

    private String fromId;

    private String toId;

    private String message;

}
