package info.nurrony.tutorials.spring.cf.service;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import info.nurrony.tutorials.spring.cf.client.CustomerClient;
import info.nurrony.tutorials.spring.cf.client.PurchaseTransactionClient;
import info.nurrony.tutorials.spring.cf.dto.CustomerResponse;
import info.nurrony.tutorials.spring.cf.dto.PurchaseTransactionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public record CustomerService(
        CustomerClient customerClient,
        PurchaseTransactionClient purchaseTransactionClient) {

    public CompletableFuture<CustomerResponse> getCustomerByIdUsingExceptionally(Integer customerId) {
        log.info("Getting customer by id {} using exceptionally.", customerId);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, isException(customerId))
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .exceptionally(ex -> {
                    log.error("Received exception {}, returning empty list.", ex.getMessage());
                    return Set.of();
                });
        return customerResponseCF
                .thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
                    customerResponse.setPurchaseTransactions(purchaseTransactions);
                    return customerResponse;
                });
    }

    public CompletableFuture<CustomerResponse> getCustomerByIdUsingExceptionallyRethrow(Integer customerId) {
        log.info("Getting customer by id {} using exceptionally and rethrow.", customerId);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, isException(customerId))
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .exceptionally(ex -> {
                    log.error("Received exception {}, throwing new exception!", ex.getMessage());
                    throw new IllegalArgumentException();
                });
        return customerResponseCF
                .thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
                    customerResponse.setPurchaseTransactions(purchaseTransactions);
                    return customerResponse;
                });
    }

    public CompletableFuture<CustomerResponse> getCustomerByIdUsingHandle(Integer customerId) {
        log.info("Getting customer by id {} using handle.", customerId);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, isException(customerId))
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .handle((response, ex) -> {
                    log.info("Executing exception handler for purchase transaction CF...");
                    if (ex != null) {
                        log.error("Received exception {}, returning empty list.", ex.getMessage());
                        return Collections.emptySet();
                    }
                    return response;
                });
        return customerResponseCF.thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
            customerResponse.setPurchaseTransactions(purchaseTransactions);
            return customerResponse;
        });

    }

    public CompletableFuture<CustomerResponse> getCustomerByIdUsingWhenComplete(Integer customerId) {
        log.info("Getting customer by id {} using when complete.", customerId);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, isException(customerId))
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .whenComplete((response, ex) -> {
                    log.info("Executing whenComplete for purchase transaction CF...");
                    if (ex != null) {
                        log.error("Received exception {}, throwing exception to consumer", ex.getMessage());
                    }
                });
        return customerResponseCF.thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
            customerResponse.setPurchaseTransactions(purchaseTransactions);
            return customerResponse;
        });
    }

    public CompletableFuture<CustomerResponse> getCustomerByIdWithOrTimeout(Integer customerId) {
        log.info("Getting customer by id {} with orTimeout", customerId);
        int timeOut = getTimeOut(customerId);
        log.info("CF timeout is {}", timeOut);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, false)
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .orTimeout(timeOut, TimeUnit.SECONDS);
        return customerResponseCF.thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
            customerResponse.setPurchaseTransactions(purchaseTransactions);
            return customerResponse;
        });
    }

    public CompletableFuture<CustomerResponse> getCustomerByIdWithCompleteOnTimeout(Integer customerId) {
        log.info("Getting customer by id {} with completeOnTimeout.", customerId);
        int timeOut = getTimeOut(customerId);
        log.info("CF timeout is {}", timeOut);
        CompletableFuture<CustomerResponse> customerResponseCF = CompletableFuture.supplyAsync(
                () -> CustomerResponse.valueOf(customerClient.getCustomerById(customerId)));
        CompletableFuture<Set<PurchaseTransactionResponse>> purchaseTransactionsCF = CompletableFuture.supplyAsync(
                () -> purchaseTransactionClient
                        .getPurchaseTransactionsByCustomerId(customerId, false)
                        .stream()
                        .map(PurchaseTransactionResponse::valueOf)
                        .collect(Collectors.toSet()))
                .completeOnTimeout(Set.of(), timeOut, TimeUnit.SECONDS);
        return customerResponseCF.thenCombine(purchaseTransactionsCF, (customerResponse, purchaseTransactions) -> {
            customerResponse.setPurchaseTransactions(purchaseTransactions);
            return customerResponse;
        });
    }

    private static int getTimeOut(Integer customerId) {
        return customerId % 2 == 0 ? 2 : 5;
    }

    private static boolean isException(Integer customerId) {
        return customerId % 2 == 0;
    }
}
