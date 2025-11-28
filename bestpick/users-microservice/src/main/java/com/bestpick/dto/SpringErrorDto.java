package com.bestpick.dto;

public record SpringErrorDto(String timestamp, int status, String error, String message, String path) {

}