package com.example.minhaloja.controle;

import java.util.Optional;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.minhaloja.modelo.Cliente;
import com.example.minhaloja.modelo.Pedido;
import com.example.minhaloja.modelo.Item;
import com.example.minhaloja.repositorios.RepositorioCliente;
import com.example.minhaloja.repositorios.RepositorioItem;
import com.example.minhaloja.repositorios.RepositorioPedido;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControladorPedido {
    @Autowired
    RepositorioPedido repositorioPedido;

    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioItem repositorioItem;

    @RequestMapping("/listaPedido")
    public ModelAndView lista() {
        ModelAndView retorno = new ModelAndView("listaPedido.html");
        Iterable<Pedido> pedidos = repositorioPedido.findAll();
        retorno.addObject("pedidos", pedidos);
        return retorno;
    }

    @RequestMapping("/paginaPedido/{idCliente}")
    public ModelAndView paginaPedido(@PathVariable("idCliente") Long idCliente, RedirectAttributes redirect) {
        ModelAndView retorno = new ModelAndView("cadastroPedido");
        Optional<Cliente> cliente = repositorioCliente.findById(idCliente);
        Iterable<Item> itens = repositorioItem.findAll();
        if (cliente.isPresent()) {
            retorno.addObject("cliente", cliente.get());
            retorno.addObject("itens", itens);
            return retorno;
        } else {
            redirect.addFlashAttribute("mensagem", "Cliente não encontrado");
            return retorno;
        }
    }

    @RequestMapping("/cadastrarPedido")
    public ModelAndView cadastrar(String idItens, Long idCliente, RedirectAttributes redirectAttributes) {
       // System.out.println("AQQUIIIIIIII  "+ pedido.getCliente().getNome());
       Optional<Cliente> cliente = repositorioCliente.findById(idCliente);
       List<Item> itens = obterItens(idItens);
       Date data = new Date();
       Pedido pedido = new Pedido(cliente.get(), itens, data, obterValorDoPedido(itens));

       repositorioPedido.save(pedido);
       redirectAttributes.addFlashAttribute("mensagem", "Cadastrado com sucesso!");
        
        ModelAndView retorno = new ModelAndView("redirect:/listaPedido");
        return retorno;
    }

    @RequestMapping("/paginaPedido2")
    public ModelAndView paginaPedido2(){
        ModelAndView retorno = new ModelAndView("cadastroPedido2");
        Iterable<Cliente> clientes = repositorioCliente.findAll();
        Iterable<Item> itens = repositorioItem.findAll();
        retorno.addObject("clientes", clientes);
        retorno.addObject("itens", itens);
        return retorno;
    }

    @RequestMapping("/cadastrarPedido2")
    public ModelAndView cadastrarPedido2(Long idCliente, String idItens, RedirectAttributes redirect){
        ModelAndView retorno;
        Optional<Cliente> cliente = repositorioCliente.findById(idCliente);
        List<Item> itens = obterItens(idItens);
        Pedido pedido = new Pedido(cliente.get(), itens, new Date(), obterValorDoPedido(itens));
        retorno = new ModelAndView("redirect:/paginaPedido2");
        repositorioPedido.save(pedido);
        redirect.addFlashAttribute("mensagem", "Venda realizada!");
        return retorno;
    }

    private List<Item> obterItens(String idItens) {
        List<Item> itens = new ArrayList<Item>();
        String ItensId[] = idItens.split(",");

        for (String itemID : ItensId) {
            Optional<Item> item = repositorioItem.findById(Long.parseUnsignedLong(itemID));
            itens.add(item.get());
        }
        return itens;
    }

    private Double obterValorDoPedido(List<Item> itens) {
        Double valor = 0.0;
        for (Item item : itens) {
            valor += item.getPreco();
        }
        return valor;
    }


}