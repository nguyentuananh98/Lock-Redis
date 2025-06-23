package com.example.demo.controller;


import com.example.demo.model.TransferRequest;
import com.example.demo.model.TransferResponse;
import com.example.demo.service.TransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/tranfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;


    @PostMapping("/money")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody TransferRequest transferRequest) {
        return new ResponseEntity<>(transferService.transfer(transferRequest), HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit() {
        return new ResponseEntity<>("abc", HttpStatus.OK);
    }
}
