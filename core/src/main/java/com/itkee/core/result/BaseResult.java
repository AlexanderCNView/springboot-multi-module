package com.itkee.core.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author rabbit
 */

@Builder
@Data
public class BaseResult<T> {

    private int code;
    private String msg;
    private T data;
}
