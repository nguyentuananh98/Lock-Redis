package com.example.demo.service;

import com.example.demo.model.TransferRequest;
import com.example.demo.model.TransferResponse;

public interface TransferService {
    TransferResponse transfer(TransferRequest transferRequest);
}
