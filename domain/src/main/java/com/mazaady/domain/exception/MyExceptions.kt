package com.mazaady.domain.exception

open class MyExceptions(message: String?) : Exception(message)
class NoInternetException(message: String?):MyExceptions(message)
class EmptyResponseException(message: String?):MyExceptions(message)
class ServerException(message: String?):MyExceptions(message)
class InternalServerException(message: String?):MyExceptions(message)
class UnAuthException(message: String?):MyExceptions(message)
