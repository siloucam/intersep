package inf.ufes.web.rest;

public class Indiciamento {

	String codigo;
	String indiciado;
	String date;
	
	public Indiciamento(String codigo, String nome, String data) {
		super();
		this.codigo = codigo;
		this.indiciado = nome;
		this.date = data;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIndiciado() {
		return indiciado;
	}

	public void setIndiciado(String indiciado) {
		this.indiciado = indiciado;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String data) {
		this.date = data;
	}
	
	
	
	
}
