package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Permissao;
import com.padawanbr.alfredfood.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissaoRepository  extends JpaRepository<Permissao, Long> {
}
