package com.dynns.cloudtecnologia.logistica.model.repository;

import com.dynns.cloudtecnologia.logistica.model.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
