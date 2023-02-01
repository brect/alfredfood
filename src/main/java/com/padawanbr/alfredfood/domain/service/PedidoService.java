package com.padawanbr.alfredfood.domain.service;

import com.padawanbr.alfredfood.domain.exception.BussinesException;
import com.padawanbr.alfredfood.domain.exception.PedidoNaoEncontradoException;
import com.padawanbr.alfredfood.domain.model.*;
import com.padawanbr.alfredfood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido consultar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    @Transactional
    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.consultar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.consultar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.consultar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.consultar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new BussinesException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao()));
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.consultar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }
}
