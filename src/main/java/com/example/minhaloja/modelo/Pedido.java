package com.example.minhaloja.modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull(message = "Cliente não pode está vazio.")
    @ManyToOne
     private Cliente cliente;
     @NotNull(message = "Selecione pelo menos um item.")
     @Size(min = 1, message = "Deve conter pelo menos um item.")
     @ManyToMany(cascade = CascadeType.ALL)
     @JoinTable(name = "Pedido_item", joinColumns = {@JoinColumn(name="Pedido_id")}, inverseJoinColumns = {@JoinColumn(name="item_id")})
    private List<Item> itens;
    private Date data;
    private Double valor;

    
    public Pedido(@NotNull(message = "Cliente não pode está vazio.") Cliente cliente,
            @NotNull(message = "Selecione pelo menos um item.") @Size(min = 1, message = "Deve conter pelo menos um item.") List<Item> itens,
            Date data, Double valor) {
        this.cliente = cliente;
        this.itens = itens;
        this.data = data;
        this.valor = valor;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

     public Cliente getCliente() {
         return cliente;
     }

     public void setCliente(Cliente cliente) {
         this.cliente = cliente;
     }

     public List<Item> getItens() {
        return itens;
     }

     public void setItens(List<Item> itens) {
         this.itens = itens;
     }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    

    

    
}