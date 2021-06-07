package com.rjet.websocket.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 分页参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MPage implements Serializable {

    private static final long serialVersionUID = -3173689038626085018L;

    @Min(value = 1, message = "size最小为1")
    private long size;

    @Min(value = 1, message = "current最小为1")
    private long current;
}
