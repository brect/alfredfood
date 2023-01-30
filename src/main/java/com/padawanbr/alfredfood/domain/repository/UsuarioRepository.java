package com.padawanbr.alfredfood.domain.repository;

import com.padawanbr.alfredfood.domain.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

}