package com.itkee.core.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author rabbit
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {

    private int code;
    private String msg;
    private T data;
}
