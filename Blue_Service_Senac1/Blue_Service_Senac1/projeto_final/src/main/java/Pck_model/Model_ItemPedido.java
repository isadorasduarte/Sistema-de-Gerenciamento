package Pck_model;

import java.math.BigDecimal;

public class Model_ItemPedido {
    private int idItemPedido;
    private int quantidade;
    private java.math.BigDecimal precoUnitario;
    private java.math.BigDecimal subtotal;
    private int idPedido;
    private int idProduto;

    public int getIdItemPedido() { 
        return idItemPedido; }
    
    public void setIdItemPedido(int idItemPedido) { 
        this.idItemPedido = idItemPedido; }
    
    public int getQuantidade() { 
        return quantidade; }
    
    public void setQuantidade(int quantidade) { 
        this.quantidade = quantidade; }
    
    public java.math.BigDecimal getPrecoUnitario() { 
        return precoUnitario; }
    
    public void setPrecoUnitario(java.math.BigDecimal precoUnitario) { 
        this.precoUnitario = precoUnitario; }
    
    public java.math.BigDecimal getSubtotal() { 
        return subtotal; }
    
    public void setSubtotal(java.math.BigDecimal subtotal) { 
        this.subtotal = subtotal; }
    
    public int getIdPedido() { 
        return idPedido; }
    
    public void setIdPedido(int idPedido) { 
        this.idPedido = idPedido; }
    
    public int getIdProduto() { 
        return idProduto; }
    
    public void setIdProduto(int idProduto) { 
        this.idProduto = idProduto; }
}
