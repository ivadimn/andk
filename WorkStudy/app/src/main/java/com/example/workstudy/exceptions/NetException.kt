package com.example.workstudy.exceptions

import java.lang.RuntimeException

class NetException(
 val msg : String
) :
 RuntimeException("Ошибка сети: $msg"){
}