package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.ExpenseType;
import com.monthlyexpenses.server.model.User;
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
    private final UserService userService;
    private final MessagesComponent messages;

    public List<ExpenseTypeResponse> getAllExpenseTypes(Long userId) {
        return expenseTypeRepository.findAllByUserIdOrderByNameAsc(userId)
                .stream().map(this::buildExpenseInfoResponse)
                .collect(Collectors.toList());
    }

    public ExpenseTypeResponse create(Long userId, String expenseTypeName) {
        User user = userService.getUserByUserId(userId);
        ExpenseType expenseType = new ExpenseType(expenseTypeName, user);

        try {
            ExpenseType expenseTypeSaved = expenseTypeRepository.saveAndFlush(expenseType);
            return buildExpenseInfoResponse(expenseTypeSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public ExpenseTypeResponse update(Long userId, String expenseTypeName, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndUserId(expenseTypeId, userId);

        try {
            expenseType.setName(expenseTypeName);
            ExpenseType expenseTypeSaved = expenseTypeRepository.saveAndFlush(expenseType);
            return buildExpenseInfoResponse(expenseTypeSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public MessageResponse delete(Long userId, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndUserId(expenseTypeId, userId);
        expenseTypeRepository.delete(expenseType);
        return MessageResponse.builder().message(messages.get("EXPENSE_TYPE_DELETED")).build();
    }

    public ExpenseType findExpenseTypeByIdAndUserId(Long expenseTypeId, Long userId) {
        return expenseTypeRepository.findByIdAndUserId(expenseTypeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_TYPE_NOT_FOUND")));
    }

    private ExpenseTypeResponse buildExpenseInfoResponse(ExpenseType expenseType) {
        return ExpenseTypeResponse.builder()
                .id(expenseType.getId())
                .name(expenseType.getName())
                .build();
    }
}
