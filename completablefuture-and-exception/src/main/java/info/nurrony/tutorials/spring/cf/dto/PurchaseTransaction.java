package info.nurrony.tutorials.spring.cf.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseTransaction {

    private String id;

    private String paymentType;

    private BigDecimal amount;

    private LocalDate createdAt;
}
