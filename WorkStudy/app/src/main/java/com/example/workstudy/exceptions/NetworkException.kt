package com.example.workstudy.exceptions

import java.lang.RuntimeException

class NetworkException(
 val msg : String
) :
 RuntimeException("Ошибка сети: $msg"){
}