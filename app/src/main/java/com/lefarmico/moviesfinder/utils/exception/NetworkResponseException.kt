package com.lefarmico.moviesfinder.utils.exception

class NetworkResponseException : Exception {
    constructor() : super()
    constructor(code: Int?, message: String?) : super(getMessage(code, message))
    constructor(code: Int?, message: String?, cause: Throwable) : super(getMessage(code, message), cause)
    constructor(cause: Throwable) : super(cause)

    companion object {
        private fun getMessage(code: Int?, message: String?) = "[CODE] $code, [MESSAGE] $message"
    }
}
