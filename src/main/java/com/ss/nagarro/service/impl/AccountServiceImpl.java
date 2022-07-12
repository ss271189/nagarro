package com.ss.nagarro.service.impl;

import com.ss.nagarro.dao.AccountDao;
import com.ss.nagarro.entity.AccountStatement;
import com.ss.nagarro.exception.AuthorizationException;
import com.ss.nagarro.exception.InvalidRequestException;
import com.ss.nagarro.model.AccountStatementRequest;
import com.ss.nagarro.model.AccountStatementResposne;
import com.ss.nagarro.model.Statement;
import com.ss.nagarro.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Autowired
    AccountDao accountDao;

     public AccountStatementResposne getAccountStatement(AccountStatementRequest request, String role) throws InvalidRequestException, AuthorizationException {
         log.info("Request: {} | Assigned Role: {}",request,role);

         AccountStatementResposne resposne;
         if(role.equalsIgnoreCase("ADMIN")){
             List<AccountStatement> accountStatements = getAccountStatements(request);
             validateAdminRequest(request);

             resposne= filterStatementForAdmin(accountStatements,request);
        } else {
            validateUserRequest(request);
            List<AccountStatement> accountStatements = getAccountStatements(request);
             resposne= filterLastThreeMonthStatement(accountStatements);
        }
        log.info("Response {}",resposne);
     return resposne;
     }



    private List<AccountStatement> getAccountStatements(AccountStatementRequest request) throws InvalidRequestException {
        List<AccountStatement> accountStatements=accountDao.getAccountStatementById(request.getAccountId());
        if(accountStatements.isEmpty()){
            log.error("Account Id, {} not found!!!", request.getAccountId());
            throw new InvalidRequestException("Account Id not valid");
        }
        return accountStatements;
    }

    private void validateUserRequest(AccountStatementRequest request) throws  AuthorizationException {
        log.debug("Validating the request for USER role");
         if(request.getToAmount()!=null || request.getFromAmount()!=null || request.getToDate() !=null || request.getFromDate()!=null){
             log.error("Role USER cannot provide filter parameters. Request {}",request);
             throw new AuthorizationException("Role USER cannot provide filter parameters");
         }
        log.info("Validation successful for USER role");
    }

    private void validateAdminRequest(AccountStatementRequest request) throws InvalidRequestException {
        log.debug("Validating the request for ADMIN role");
        // To check valid date range
        if (request.getFromDate() != null || request.getToDate() != null) {
            if (request.getFromDate() == null || request.getToDate() == null) {
                log.error("Both dates should be passed");
                throw new InvalidRequestException("Both dates should be passed");
            }
            if (request.getToDate().isBefore(request.getFromDate())) {
                log.error("To date should not be after from date");
                throw new InvalidRequestException("To date should not be after from date");
            }
            if (request.getToDate().isAfter(LocalDate.now())) {
                log.error("To date should not be in future");
                throw new InvalidRequestException("To date should not be in future");
            }

        }

        // To check valid amount range
        if (request.getFromAmount() != null || request.getToAmount() != null) {
            if (request.getToAmount() == null || request.getFromAmount() == null) {
                log.error("Both to and from amount range should be passed");
                throw new InvalidRequestException("Both to and from amount range should be passed");
            }
            if (request.getToAmount() < request.getFromAmount()) {
                log.error("From amount should not be greater than to amount range");
                throw new InvalidRequestException("From amount should not be greater than to amount range");
            }
        }
        log.info("Validation successful for ADMIN role");
    }


    private AccountStatementResposne filterLastThreeMonthStatement(List<AccountStatement> accountStatements){

         UnaryOperator<String> maskedAccountNumber=s-> "XXXXXXXXX".concat(s.substring(9));

         AccountStatementResposne response=AccountStatementResposne.builder()
                 .accountId(accountStatements.get(0).getId())
                 .accountNumber(maskedAccountNumber.apply(accountStatements.get(0).getAccountNumber()))
                 .build();

         LocalDate dateBeforeThreeMonths=LocalDate.now().minusMonths(3);

         final List<Statement> statements =new ArrayList<>();
         accountStatements.stream().forEach(acc-> {
             if(acc.getDate().isAfter(dateBeforeThreeMonths)){
                 statements.add( Statement.builder()
                         .amount(acc.getAmount())
                         .date(acc.getDate())
                         .build());
             }
         });

         response.setStatements(statements);

         return response;
      }

      private AccountStatementResposne filterStatementForAdmin(List<AccountStatement> accountStatements,AccountStatementRequest request){

          UnaryOperator<String> maskedAccountNumber=s-> "XXXXXXXXX".concat(s.substring(9));

          AccountStatementResposne response=AccountStatementResposne.builder()
                  .accountId(accountStatements.get(0).getId())
                  .accountNumber(maskedAccountNumber.apply(accountStatements.get(0).getAccountNumber()))
                  .build();


          final List<Statement> statements =new ArrayList<>();
          accountStatements.stream().forEach(acc-> {
              boolean dateInBetween=true;
              if(request.getToDate()!=null && request.getFromDate() != null){
                  dateInBetween = ((acc.getDate().isAfter(request.getFromDate())) && (acc.getDate().isBefore(request.getToDate())));
              }

              boolean amountInRange=true;
              if(request.getFromAmount()!=null && request.getToAmount() != null){
                  amountInRange = (acc.getAmount()>=request.getFromAmount() && acc.getAmount()<=request.getToAmount() );
              }

              if(dateInBetween && amountInRange){
                  statements.add( Statement.builder()
                          .amount(acc.getAmount())
                          .date(acc.getDate())
                          .build());
              }
          });

          response.setStatements(statements);

          return response;
      }


}
