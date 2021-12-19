package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.model.ExpenseType;
import com.monthlyexpenses.server.repository.ExpenseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final CustomerService customerService;
    private final MessagesComponent messages;

    public List<ExpenseTypeResponse> getAllExpenseTypes(Long customerId) {
        return expenseTypeRepository.findAllByCustomerIdOrderByNameAsc(customerId)
                .stream().map(this::buildExpenseInfoResponse)
                .collect(Collectors.toList());
    }

    public ExpenseTypeResponse create(Long customerId, String expenseTypeName) {
        Customer customer = customerService.findCustomerByIdOrElseThrow(customerId);

        ExpenseType expenseType = ExpenseType.builder()
                .name(expenseTypeName)
                .customer(customer)
                .build();

        try {
            ExpenseType expenseTypeSaved = expenseTypeRepository.saveAndFlush(expenseType);
            return buildExpenseInfoResponse(expenseTypeSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public ExpenseTypeResponse update(Long customerId, String expenseTypeName, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndCustomerIdOrElseThrow(expenseTypeId, customerId);
        expenseType.setName(expenseTypeName);

        try {
            ExpenseType expenseTypeSaved = expenseTypeRepository.saveAndFlush(expenseType);
            return buildExpenseInfoResponse(expenseTypeSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public MessageResponse delete(Long customerId, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndCustomerIdOrElseThrow(expenseTypeId, customerId);
        expenseTypeRepository.delete(expenseType);
        return MessageResponse.builder().message(messages.get("EXPENSE_TYPE_DELETED")).build();
    }

    public ExpenseType findExpenseTypeByIdAndCustomerIdOrElseThrow(Long expenseTypeId, Long customerId) {
        return expenseTypeRepository.findByIdAndCustomerId(expenseTypeId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_TYPE_NOT_FOUND")));
    }

    private ExpenseTypeResponse buildExpenseInfoResponse(ExpenseType expenseType) {
        return ExpenseTypeResponse.builder()
                .id(expenseType.getId())
                .name(expenseType.getName())
                .build();
    }
}
