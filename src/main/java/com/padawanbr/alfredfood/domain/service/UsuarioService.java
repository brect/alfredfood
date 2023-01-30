package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.UsuarioNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.Usuario;
import com.padawanbr.alfredfood.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.detach(usuario);

        final Optional<Usuario> userByEmail = usuarioRepository.findByEmail(usuario.getEmail());

        if (userByEmail.isPresent() && !userByEmail.get().equals(usuario)){
            throw new BussinesException(String.format("Já existe usuário cadastrado com o e-mail %s", usuario.getEmail()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = consultar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new BussinesException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    public Usuario consultar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    public List<Usuario> consultarTodos() {
        return usuarioRepository.findAll();
    }
}
