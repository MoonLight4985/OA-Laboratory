package com.oaacm.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ACMException extends RuntimeException{

    private Integer code;//状态码
    private String msg;//异常信息

}
