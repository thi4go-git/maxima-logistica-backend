package com.dynns.cloudtecnologia.logistica.model.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 14, unique = true, updatable = false)
    private String cnpj;

    @OneToOne
    @JoinColumn(name = "id_endereco")
    //@JsonIgnore
    private Endereco endereco;

}
