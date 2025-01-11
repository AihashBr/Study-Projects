package com.br.sqlite;

import java.util.ArrayList;

public class conteudoDb {
    private int id;
    private String nome;
    private int quantidade;
    public conteudoDb(int id, String nome, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
    }
    public int getId() {return id;}
    public String getNome() {return nome;}
    public int getQuantidade() {return  quantidade;}
    public String getText() {return "nome: " + nome + " | quantidade: " + quantidade;}
}