package com.javait.models;

public record ApiError(
        String code,
        String message
) {}