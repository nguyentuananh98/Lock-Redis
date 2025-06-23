package com.example.demo.service.impl;

import com.example.demo.entity.BankAccount;
import com.example.demo.model.TransferRequest;
import com.example.demo.model.TransferResponse;
import com.example.demo.repository.BankAccountRepository;
import com.example.demo.service.TransferService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private BankAccountRepository repo;

    @Autowired
    private RedissonClient redisson;

    @Override
    @Transactional
    public TransferResponse transfer(TransferRequest transferRequest) {
        //tránh deadlock: sort ID để lock theo thứ tự
        long fromId = transferRequest.getFromId();
        long toId = transferRequest.getToId();
        int amount = transferRequest.getAmount();

        Long low = Math.min(fromId, toId);
        Long high = Math.max(fromId, toId);

        RLock lock1 = redisson.getLock("account:lock:" + low);
        RLock lock2 = redisson.getLock("account:lock:" + high);

        try {
            boolean locked = lock1.tryLock(10, 5, TimeUnit.SECONDS) &&
                    lock2.tryLock(10, 5, TimeUnit.SECONDS);
            if (!locked) {
                return new TransferResponse(false);
            }

            BankAccount from = repo.findById(fromId).orElseThrow();
            BankAccount to = repo.findById(toId).orElseThrow();

            if (from.getBalance() < amount) return new TransferResponse(false);

            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            return new TransferResponse(true);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new TransferResponse(false);
        } finally {
            if (lock2.isHeldByCurrentThread()) lock2.unlock();
            if (lock1.isHeldByCurrentThread()) lock1.unlock();
        }
    }
}

