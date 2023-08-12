package com.dynns.cloudtecnologia.logistica.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8)
    private String cep;

    @Column(nullable = false, length = 100)
    private String logradouro;

    @Column(length = 100)
    private String complemento;

    @Column(nullable = false, length = 50)
    private String bairro;

    @Column(nullable = false, length = 50)
    private String localidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false, length = 10)
    private String ibge;

    @Column(length = 50)
    private String gia;

    @Column(length = 2)
    private String ddd;

    @Column(length = 50)
    private String siafi;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;
}
