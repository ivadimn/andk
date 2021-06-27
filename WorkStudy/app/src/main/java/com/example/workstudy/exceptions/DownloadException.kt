package com.example.workstudy.exceptions

import java.lang.Exception
import java.lang.RuntimeException

class DownloadException(
   val msg : String
)
   : RuntimeException("Ошибка загрузки файла: $msg") {
}