package com.example.apiauth.Contracts;

public interface UseCase<I, O> {
    O execute(I input);
}