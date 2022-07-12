package com.ss.nagarro.dao.impl;

import com.ss.nagarro.dao.AccountDao;
import com.ss.nagarro.entity.AccountStatement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
@Slf4j
public class AccountDaoImpl implements AccountDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public List<AccountStatement> getAccountStatementById(String accountId){
        log.info("Requseted accounts details for Id: {}",accountId);
        String sql="SELECT a.id,a.account_type,a.account_number,s.datefield,s.amount FROM account a JOIN statement s on a.id=s.account_id where a.id = ?";
        log.debug("Query to be executed : {}",sql);
        List<AccountStatement> accountList = jdbcTemplate.query(sql,
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement preparedStatement) throws
                            SQLException {
                        preparedStatement.setString(1, accountId);
                    }
                }, new RowMapper<AccountStatement>() {
                    @Override
                    public AccountStatement mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return AccountStatement.builder()
                                .id(rs.getString("id"))
                                .accountType(rs.getString("account_type"))
                                .accountNumber(rs.getString("account_number"))
                                .date(LocalDate.parse(rs.getString("datefield"), formatter))
                                .amount(Double.valueOf(rs.getString("amount")))
                                .build();
                    }
                });
        log.info("Total numbers of records fetched for Account Id:{} are {}",accountId,accountList.size());
        return accountList;
    }

}
