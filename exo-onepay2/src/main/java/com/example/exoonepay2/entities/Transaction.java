package com.example.exoonepay2.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @Column(name = "status")
    private Status status;

    /**
     * A transaction can have multiple order line
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_id")
    private List<OrderLine> orderLineList;

    @Getter
    @RequiredArgsConstructor
    public enum PaymentType {
        CREDIT_CARD,
        GIFT_CARD,
        PAYPAL
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        NEW,
        AUTHORIZED,
        CAPTURED
    }
}
