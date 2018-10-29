package inf.ufes.web.rest;

public class Statistic {

    private String query;
    private float value;
    private String outputSPARQL;
    private String outputSQL;
    
    public Statistic(float value) {
    	this.value = value;
    	this.query = "";
    	this.outputSPARQL = "";
    	this.outputSQL = "";
     }
    
	public Statistic(String query,float value, String sparql, String sql) {
		super();
		this.query = query;
		this.value = value;
		this.outputSPARQL = sparql;
		this.outputSQL = sql;
	}

	public String getOutputSPARQL() {
		return outputSPARQL;
	}

	public void setOutputSPARQL(String outputSPARQL) {
		this.outputSPARQL = outputSPARQL;
	}

	public String getOutputSQL() {
		return outputSQL;
	}

	public void setOutputSQL(String outputSQL) {
		this.outputSQL = outputSQL;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}
    
    
    
}
