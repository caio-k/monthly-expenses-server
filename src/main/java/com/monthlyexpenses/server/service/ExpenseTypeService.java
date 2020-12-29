package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.error.exception.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.ExpenseType;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseTypeService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final UserService userService;
    private final MessagesComponent messages;

    @Autowired
    public ExpenseTypeService(ExpenseTypeRepository expenseTypeRepository, UserService userService,
                              MessagesComponent messages) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.userService = userService;
        this.messages = messages;
    }

    public List<ExpenseTypeResponse> getAllExpenseTypes(Long userId) {
        List<ExpenseTypeResponse> expenseTypeResponses = new ArrayList<>();
        List<ExpenseType> expenseTypes = expenseTypeRepository.findAllByUserIdOrderByNameAsc(userId);

        for (ExpenseType expenseType : expenseTypes) {
            expenseTypeResponses.add(new ExpenseTypeResponse(expenseType.getId(), expenseType.getName()));
        }

        return expenseTypeResponses;
    }

    public ResponseEntity<?> create(Long userId, String expenseTypeName) {
        User user = userService.getUserByUserId(userId);
        ExpenseType expenseType = new ExpenseType(expenseTypeName, user);

        try {
            expenseTypeRepository.save(expenseType);
            return ResponseEntity.ok(new ExpenseTypeResponse(expenseType.getId(), expenseType.getName()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> update(Long userId, String expenseTypeName, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndUserId(expenseTypeId, userId);

        try {
            expenseType.setName(expenseTypeName);
            expenseTypeRepository.save(expenseType);
            return ResponseEntity.ok(new ExpenseTypeResponse(expenseType.getId(), expenseType.getName()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("EXPENSE_TYPE_NAME_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> delete(Long userId, Long expenseTypeId) {
        ExpenseType expenseType = findExpenseTypeByIdAndUserId(expenseTypeId, userId);
        expenseTypeRepository.delete(expenseType);
        return ResponseEntity.ok(new MessageResponse(messages.get("EXPENSE_TYPE_DELETED")));
    }

    public ExpenseType findExpenseTypeByIdAndUserId(Long expenseTypeId, Long userId) {
        return expenseTypeRepository.findByIdAndUserId(expenseTypeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_TYPE_NOT_FOUND")));
    }
}
