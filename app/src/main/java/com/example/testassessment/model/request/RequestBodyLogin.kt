package com.example.testassessment.model.request

data class RequestBodyLogin(
    var username: String,
    var password: String,
    var token: String
)