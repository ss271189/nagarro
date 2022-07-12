package com.ss.nagarro.service;


import com.ss.nagarro.dao.AccountDao;
import com.ss.nagarro.entity.AccountStatement;
import com.ss.nagarro.exception.AuthorizationException;
import com.ss.nagarro.exception.InvalidRequestException;
import com.ss.nagarro.model.AccountStatementRequest;
import com.ss.nagarro.model.AccountStatementResposne;
import com.ss.nagarro.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {


    @Mock
    AccountDao accountDao;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    public void testGetAccountStatementForAdmin() throws AuthorizationException, InvalidRequestException {

        AccountStatementRequest request= AccountStatementRequest.builder()
                .accountId("1")
                .fromAmount(100)
                .toAmount(300)
                .fromDate(LocalDate.now().minusYears(1))
                .toDate(LocalDate.now())
                .build();

        AccountStatement accountStatement1=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(150.0)
                .id("1")
                .date(LocalDate.now().minusDays(15))
                .build();

        AccountStatement accountStatement2=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(350.0)
                .id("1")
                .date(LocalDate.now().minusDays(700))
                .build();
        Mockito.when(accountDao.getAccountStatementById(Mockito.any())).thenReturn(List.of(accountStatement1,accountStatement2));
        AccountStatementResposne resposne=accountService.getAccountStatement(request,"ADMIN");
        Assertions.assertEquals(1,resposne.getStatements().size());
    }


    @Test
    public void testGetAccountStatementForUser() throws AuthorizationException, InvalidRequestException {

        AccountStatementRequest request= AccountStatementRequest.builder()
                .accountId("1")
                .build();

        AccountStatement accountStatement1=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(150.0)
                .id("1")
                .date(LocalDate.now().minusDays(15))
                .build();

        AccountStatement accountStatement2=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(350.0)
                .id("1")
                .date(LocalDate.now().minusDays(700))
                .build();
        Mockito.when(accountDao.getAccountStatementById(Mockito.any())).thenReturn(List.of(accountStatement1,accountStatement2));
        AccountStatementResposne resposne=accountService.getAccountStatement(request,"USER");
        Assertions.assertEquals(1,resposne.getStatements().size());
    }


    @Test(expected = AuthorizationException.class)
    public void testGetAccountStatementForUserExceptiom() throws AuthorizationException, InvalidRequestException {

        AccountStatementRequest request= AccountStatementRequest.builder()
                .accountId("1")
                .fromAmount(100)
                .toAmount(300)
                .fromDate(LocalDate.now().minusYears(1))
                .toDate(LocalDate.now())
                .build();

        AccountStatement accountStatement1=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(150.0)
                .id("1")
                .date(LocalDate.now().minusDays(15))
                .build();

        AccountStatement accountStatement2=AccountStatement.builder()
                .accountNumber("123456789")
                .accountType("Saving")
                .amount(350.0)
                .id("1")
                .date(LocalDate.now().minusDays(700))
                .build();
        AccountStatementResposne resposne=accountService.getAccountStatement(request,"USER");
    }
}
