package com.DWmarket.market.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "SalesBulletinBoard")
@Getter
@Setter
@ToString
public class SalesBulletinBoard {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
