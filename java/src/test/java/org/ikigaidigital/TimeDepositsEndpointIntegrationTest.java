package org.ikigaidigital;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TimeDepositsEndpointIntegrationTest extends TimeDepositDatabase {

    @DynamicPropertySource
    static void dataSource(DynamicPropertyRegistry registry) {
        POSTGRES.start();
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void restoreSampleBalances() throws SQLException {
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE \"timeDeposits\" SET balance = ? WHERE id = ?")) {
            restore(statement, 1, "1234567.00");
            restore(statement, 2, "8000.00");
            restore(statement, 3, "25000.50");
        }
    }

    @Test
    void returnsTheSampleTimeDeposits() throws Exception {
        mockMvc.perform(get("/time-deposits"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                            "id": 1,
                            "planType": "basic",
                            "balance": "1234567.00",
                            "days": 45,
                            "withdrawals": [
                              {
                                "id": 1,
                                "timeDepositId": 1,
                                "amount": "100.50",
                                "date": "2026-01-15"
                              },
                              {
                                "id": 2,
                                "timeDepositId": 1,
                                "amount": "25.00",
                                "date": "2026-02-01"
                              }
                            ]
                          },
                          {
                            "id": 2,
                            "planType": "student",
                            "balance": "8000.00",
                            "days": 200,
                            "withdrawals": []
                          },
                          {
                            "id": 3,
                            "planType": "premium",
                            "balance": "25000.50",
                            "days": 60,
                            "withdrawals": [
                              {
                                "id": 3,
                                "timeDepositId": 3,
                                "amount": "500.00",
                                "date": "2026-03-10"
                              }
                            ]
                          }
                        ]
                        """, true));
    }

    @Test
    void updatesEveryBalanceForOneMonth() throws Exception {
        String updated = """
                [
                  {
                    "id": 1,
                    "planType": "basic",
                    "balance": "1235595.81",
                    "days": 45,
                    "withdrawals": [
                      {
                        "id": 1,
                        "timeDepositId": 1,
                        "amount": "100.50",
                        "date": "2026-01-15"
                      },
                      {
                        "id": 2,
                        "timeDepositId": 1,
                        "amount": "25.00",
                        "date": "2026-02-01"
                      }
                    ]
                  },
                  {
                    "id": 2,
                    "planType": "student",
                    "balance": "8020.00",
                    "days": 200,
                    "withdrawals": []
                  },
                  {
                    "id": 3,
                    "planType": "premium",
                    "balance": "25104.67",
                    "days": 60,
                    "withdrawals": [
                      {
                        "id": 3,
                        "timeDepositId": 3,
                        "amount": "500.00",
                        "date": "2026-03-10"
                      }
                    ]
                  }
                ]
                """;

        mockMvc.perform(post("/time-deposits/balance-updates"))
                .andExpect(status().isOk())
                .andExpect(content().json(updated, true));
        mockMvc.perform(get("/time-deposits"))
                .andExpect(status().isOk())
                .andExpect(content().json(updated, true));
    }

    private static void restore(PreparedStatement statement, int id, String balance) throws SQLException {
        statement.setBigDecimal(1, new BigDecimal(balance));
        statement.setInt(2, id);
        statement.executeUpdate();
    }
}
