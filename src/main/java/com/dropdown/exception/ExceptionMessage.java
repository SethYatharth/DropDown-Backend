package com.dropdown.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionMessage
{
    private String error;
    private String detail;
    private LocalDateTime timeStamp;
}
