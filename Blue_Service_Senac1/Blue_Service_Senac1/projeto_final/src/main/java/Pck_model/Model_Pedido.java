package Pck_model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Model_Pedido {
    private int idPedido;
    private Date dataPedido;
    private String origem;
    private BigDecimal valorTotal;
    private int idCliente;
    private int idFuncionario;
    private String nomeCliente;
    private List<String> produtos;

    public int getIdPedido() { 
        return idPedido; }
    
    public void setIdPedido(int idPedido) { 
        this.idPedido = idPedido; }
    
    public Date getDataPedido() { 
        return dataPedido; }
    
    public void setDataPedido(Date dataPedido) { 
        this.dataPedido = dataPedido; }
    
    public String getOrigem() { 
        return origem; }
    
    public void setOrigem(String origem) { 
        this.origem = origem; }
    
    public java.math.BigDecimal getValorTotal() { 
        return valorTotal; }
    
    public void setValorTotal(java.math.BigDecimal valorTotal) { 
        this.valorTotal = valorTotal; }
    
    public int getIdCliente() { 
        return idCliente; }
    
    public void setIdCliente(int idCliente) { 
        this.idCliente = idCliente; }
    
    public int getIdFuncionario() { 
        return idFuncionario; }
    
    public void setIdFuncionario(int idFuncionario) { 
        this.idFuncionario = idFuncionario; }

        public String getNomeCliente() {
    return nomeCliente;
}

public void setNomeCliente(String nomeCliente) {
    this.nomeCliente = nomeCliente;
}

public List<String> getProdutos() {
    return produtos;
}

public void setProdutos(List<String> produtos) {
    this.produtos = produtos;
}   
}

