package com.ss.nagarro.dao;


import com.ss.nagarro.dao.impl.AccountDaoImpl;
import com.ss.nagarro.entity.AccountStatement;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class AccountDaoImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    AccountDaoImpl accountDao;

    @Test
    public void testGetAccountStatementById(){
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
        Mockito.when(jdbcTemplate.query(Mockito.anyString(),Mockito.any(PreparedStatementSetter.class),Mockito.any(RowMapper.class))).thenReturn(List.of(accountStatement1,accountStatement2));
         List<AccountStatement> list=accountDao.getAccountStatementById("1");
        Assertions.assertNotNull(list);
    }
}
