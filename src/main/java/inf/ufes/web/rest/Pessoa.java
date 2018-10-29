package inf.ufes.web.rest;

public class Pessoa {

	String nome;
	String cpf;
	String condenacao;
	
	public Pessoa(String nome, String cpf,String condenacao) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.condenacao = condenacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCondenacao() {
		return condenacao;
	}
	public void setCondenacao(String condenacao) {
		this.condenacao = condenacao;
	}
	
	
	
}
