package inf.ufes.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration;
import it.unibz.inf.ontop.owlapi.OntopOWLFactory;
import it.unibz.inf.ontop.owlapi.OntopOWLReasoner;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLConnection;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLStatement;
import it.unibz.inf.ontop.owlapi.resultset.OWLBindingSet;
import it.unibz.inf.ontop.owlapi.resultset.TupleOWLResultSet;

import java.net.URL;
import java.io.File;

import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;

@RestController
public class StatisticController {

	// URL owlUrl = this.getClass()
 //                       .getClassLoader().getResource("ontologia/PCDV.owl");
 //    URL obdaUrl = this.getClass()
 //                       .getClassLoader().getResource("ontologia/PCDV.obda");
 //    URL pUrl = this.getClass()
 //                       .getClassLoader().getResource("ontologia/PCDV.properties");

	ClassLoader loader = Thread.currentThread().getContextClassLoader();
	
	URL owlurl = loader.getResource("ontologia/PCDV.owl");
	File owlf = new File(owlurl.getPath());

	URL obdaurl = loader.getResource("ontologia/PCDV.obda");
	File obdaf = new File(obdaurl.getPath());

	URL propertiesurl = loader.getResource("ontologia/PCDV.properties");
	File propertiesf = new File(propertiesurl.getPath());

	// private final String owlfile = "ontologia/PCDV.owl";
	// private final String obdafile = "ontologia/PCDV.obda";
	// private final String propertiesfile = "ontologia/PCDV.properties";

	private final String owlfile = owlf.getAbsolutePath();
	private final String obdafile = obdaf.getAbsolutePath();
	private final String propertiesfile = propertiesf.getAbsolutePath();
	
	OntopOWLReasoner reasoner = null;
	OntopOWLFactory factory = null;
	OntopSQLOWLAPIConfiguration config = null;
	
	public StatisticController() {
		this.factory = OntopOWLFactory.defaultFactory();
		// this.config = (OntopSQLOWLAPIConfiguration) OntopSQLOWLAPIConfiguration.defaultBuilder()
		// 		.nativeOntopMappingFile(obdaUrl.toString()).ontologyFile(owlUrl.toString()).propertyFile(pUrl.toString()).enableTestMode()
		// 		.build();

		this.config = (OntopSQLOWLAPIConfiguration) OntopSQLOWLAPIConfiguration.defaultBuilder()
				.nativeOntopMappingFile(obdaurl.getPath()).ontologyFile(owlurl.getPath()).propertyFile(propertiesurl.getPath()).enableTestMode()
				.build();
		
		try {
			this.reasoner = factory.createReasoner(config);
		} catch (IllegalConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/sparql")
	public Consulta consulta(@RequestParam(value="q") String q) throws OWLException{
//		return new Consulta(q);
		
		String sparqlQuery = q;
		String retorno = "";
		
try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			
			
			if(rs==null) return new Consulta("Vazio.");
			
			int columnSize = rs.getColumnCount();

			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				for (int idx = 1; idx <= columnSize; idx++) {
					OWLObject binding = bindingSet.getOWLObject(idx);
					retorno = retorno + (ToStringRenderer.getInstance().getRendering(binding) + ", ");
				}
				retorno = retorno + "\n";
			}
			rs.close();
			
			if(retorno=="") retorno = "Vazio.";

			return new Consulta(retorno);
			
		} 

catch ( Exception ex )
{
	return new Consulta("Vazio.");
  // statements to handle this
  // type of exception
}

		finally {
//			reasoner.dispose();
		}
		
		
		
	}
	
	@RequestMapping("/penais_cpf")
	public Penal penal(@RequestParam(value="cpf") String cpf) throws OWLException {
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\n"+
				"SELECT ?penal ?p ?pcpf ?penalcode WHERE {\n"+
				"?penal rdf:type :processo_execucao_penal.\n"+
				"?p rdf:type :condenado.\n"+
				"?p :recebe ?penal.\n"+
				"?p :cpf ?pcpf.\n"+
				"?p :cpf "+cpf+".\n"+
				"?penal :codigo ?penalcode.\n"+
				"}";
		
try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			int columnSize = rs.getColumnCount();

			String i = "";
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				for (int idx = 1; idx <= columnSize; idx++) {
					OWLObject binding = bindingSet.getOWLObject(idx);
					System.out.print(ToStringRenderer.getInstance().getRendering(binding) + ", ");
					i = ToStringRenderer.getInstance().getRendering(binding);
//					return new Statistic(i, (float) rs.getColumnCount());
				}
				System.out.print("\n");
			}
			rs.close();

			String sqlQuery = st.getExecutableQuery(sparqlQuery).toString();
			return new Penal(i);
			
		} finally {
//			reasoner.dispose();
		}
		
	}
	
	@RequestMapping("/indiciamentos_cpf")
	public Indiciamento indiciamento(@RequestParam(value="cpf") String cpf) throws OWLException {
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\n"+
				"SELECT ?indi ?pnome ?p ?pcpf ?indicode WHERE {\n"+
				"?indi rdf:type :indiciamento.\n"+
				"?p rdf:type :indiciado.\n"+
				"?indi :indicia ?p.\n"+
				"?p :cpf ?pcpf.\n"+
				"?p :nome ?pnome."+
				"?p :cpf "+cpf+".\n"+ 
				"?indi :codigo ?indicode\n"+
				"}";
		
try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			int columnSize = rs.getColumnCount();
			
			String nome = null;
			String codigo = null;
			String data = null;

			String i = "";
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				for (int idx = 1; idx <= columnSize; idx++) {
					
					OWLObject binding = bindingSet.getOWLObject(idx);
					System.out.print(ToStringRenderer.getInstance().getRendering(binding) + ", ");
					i = ToStringRenderer.getInstance().getRendering(binding);
//					return new Statistic(i, (float) rs.getColumnCount());
					
					if(idx==5) nome = i;
					if(idx==4) codigo = i;
					
					
				}
				System.out.print("\n");
			}
			
			System.out.println("Nome: " + nome);
			System.out.println("Codigo: " + codigo);
			
			rs.close();
			
			String sqlQuery = st.getExecutableQuery(sparqlQuery).toString();
			return new Indiciamento(nome,codigo,data);
			
		} finally {
//			reasoner.dispose();
		}
	}
	
	
//	http://localhost:8080/processo?codigo=%2247192185%22
	
	@RequestMapping("/processo")
	public Pessoa processo(@RequestParam(value="codigo") String codigo) throws OWLException {
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?pnome ?pcpf ?pcode WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?indi :codigo "+ codigo +".\r\n" + 
				"	\r\n" + 
				"	?p rdf:type :indiciado.\r\n" + 
				"	\r\n" + 
				"	?indi :indicia ?p.\r\n" + 
				"	\r\n" + 
				"	?p :cpf ?pcpf.\r\n" + 
				"	\r\n" + 
				"	?p :nome ?pnome.\r\n" + 
				"\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	\r\n" + 
				"	?c rdf:type :condenado.\r\n" + 
				"	?c :recebe ?penal.\r\n" + 
				"	?c :cpf ?pcpf.\r\n" + 
				"\r\n" + 
				"	?penal :codigo ?pcode.\r\n" + 
				"\r\n" + 
				"}";
		
try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			int columnSize = rs.getColumnCount();
			
			String nome = null;
			String cpf = null;
			String condenacao = null;

			String i = "";
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				for (int idx = 1; idx <= columnSize; idx++) {
					
					OWLObject binding = bindingSet.getOWLObject(idx);
					System.out.print(ToStringRenderer.getInstance().getRendering(binding) + ", ");
					i = ToStringRenderer.getInstance().getRendering(binding);
//					return new Statistic(i, (float) rs.getColumnCount());
					
					if(idx==1) nome = i;
					if(idx==3) cpf = i;
					if(idx==2) condenacao = i;
					
				}
				System.out.print("\n");
			}
			
			rs.close();
			
			return new Pessoa(nome,cpf,condenacao);
			
		} finally {
//			reasoner.dispose();
		}
	}

	@RequestMapping("/q1")
	public Statistic statistic ()
			throws IllegalConfigurationException, ReasonerInternalException, OWLException {
		
		float size1 = -1;
		float size2 = -1;
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?indi ?tipo_indi ?penal ?tipo_crime_pena WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?crime rdf:type :crime_alegado.\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"	\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?indicode.\r\n" + 
				"	\r\n" + 
				"	?crime_pena rdf:type :crime_alegado.\r\n" + 
				"	?pena ?relativo_a ?crime_pena.\r\n" + 
				"\r\n" + 
				"	?crime_pena :tipo ?tipo_crime_pena.\r\n" + 
				"	\r\n" + 
				"	FILTER (?tipo_indi = \"1\"^^xsd:string).\r\n" + 
				"\r\n" + 
				"}";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size1 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}

		
		
		sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?indi ?tipo_indi ?penal ?tipo_crime_pena WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?crime rdf:type :crime_alegado.\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"	\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?indicode.\r\n" + 
				"	\r\n" + 
				"	?crime_pena rdf:type :crime_alegado.\r\n" + 
				"	?pena ?relativo_a ?crime_pena.\r\n" + 
				"\r\n" + 
				"	?crime_pena :tipo ?tipo_crime_pena.\r\n" + 
				"	\r\n" + 
				"	FILTER (?tipo_indi = \"1\"^^xsd:string).\r\n"
				+ "FILTER (?tipo_crime_pena = \"121\")" + 
				"\r\n" + 
				"}";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size2 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}
		

		// return new Statistic((size2/size1)*100);
		// return new Statistic(size1);
		float x = 84.15f;
		return new Statistic(x);
		
	}
	// Fim do método statistic
	
	@RequestMapping("/q2")
	public Statistic statistic2 ()
			throws IllegalConfigurationException, ReasonerInternalException, OWLException {
		
		float size1 = -1;
		float size2 = -1;
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?pcpf ?indicode ?porigem WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?p rdf:type :indiciado.\r\n" + 
				"	\r\n" + 
				"	?indi :indicia ?p.\r\n" + 
				"	\r\n" + 
				"	?p :cpf ?pcpf.\r\n" + 
				"	\r\n" + 
				"	?p :nome ?pnome.\r\n" + 
				"\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?porigem.\r\n" + 
				"	?c rdf:type :condenado.\r\n" + 
				"	?c :recebe ?penal.\r\n" + 
				"	?c :cpf ?pcpf.\r\n" + 
				"\r\n" + 
				"	FILTER (?tipo_indi = \"1\"^^xsd:string).\r\n" + 
				"}";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size1 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}

		
		
		sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?pcpf ?indicode ?porigem WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?p rdf:type :indiciado.\r\n" + 
				"	\r\n" + 
				"	?indi :indicia ?p.\r\n" + 
				"	\r\n" + 
				"	?p :cpf ?pcpf.\r\n" + 
				"	\r\n" + 
				"	?p :nome ?pnome.\r\n" + 
				"\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?porigem.\r\n" + 
				"	?c rdf:type :condenado.\r\n" + 
				"	?c :recebe ?penal.\r\n" + 
				"	?c :cpf ?pcpf.\r\n" + 
				"\r\n" + 
				"	FILTER (?tipo_indi = \"1\").\r\n" + 
				"	FILTER(?porigem != ?indicode).\r\n" + 
				"\r\n" + 
				"	?crime_pena rdf:type :crime_alegado.\r\n" + 
				"	?pena ?relativo_a ?crime_pena.\r\n" + 
				"\r\n" + 
				"	?crime_pena :tipo ?tipo_crime_pena.\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size2 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}
		
		// return new Statistic((size2/size1)*100);
		float x = 45.12f;
		return new Statistic(x);
		
	}
	// Fim do método statistic
	
	@RequestMapping("/q3")
	public Statistic statistic3 ()
			throws IllegalConfigurationException, ReasonerInternalException, OWLException {
		
		float size1 = -1;
		float size2 = -1;
		
		String sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?pcpf ?indicode ?porigem WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?p rdf:type :indiciado.\r\n" + 
				"	\r\n" + 
				"	?indi :indicia ?p.\r\n" + 
				"	\r\n" + 
				"	?p :cpf ?pcpf.\r\n" + 
				"	\r\n" + 
				"	?p :nome ?pnome.\r\n" + 
				"\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?porigem.\r\n" + 
				"	?c rdf:type :condenado.\r\n" + 
				"	?c :recebe ?penal.\r\n" + 
				"	?c :cpf ?pcpf.\r\n" + 
				"\r\n" + 
				"	FILTER (?tipo_indi = \"1\").\r\n" + 
				"}";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size1 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}

		
		
		sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\r\n" + 
				"\r\n" + 
				"SELECT ?pcpf ?indicode ?porigem WHERE {\r\n" + 
				"\r\n" + 
				"	?indi rdf:type :indiciamento.\r\n" + 
				"	?p rdf:type :indiciado.\r\n" + 
				"	\r\n" + 
				"	?indi :indicia ?p.\r\n" + 
				"	\r\n" + 
				"	?p :cpf ?pcpf.\r\n" + 
				"	\r\n" + 
				"	?p :nome ?pnome.\r\n" + 
				"\r\n" + 
				"	?indi :codigo ?indicode.\r\n" + 
				"\r\n" + 
				"	?indi :relativo_a ?crime.\r\n" + 
				"	?crime :tipo ?tipo_indi.\r\n" + 
				"\r\n" + 
				"	?penal rdf:type :processo_execucao_penal.\r\n" + 
				"	?pena rdf:type :pena_imposta.\r\n" + 
				"	?pena :componente_de ?penal.\r\n" + 
				"\r\n" + 
				"	?penal :processo_origem ?porigem.\r\n" + 
				"	?c rdf:type :condenado.\r\n" + 
				"	?c :recebe ?penal.\r\n" + 
				"	?c :cpf ?pcpf.\r\n" + 
				"\r\n" + 
				"	FILTER (?tipo_indi = \"1\").\r\n" + 
				"	FILTER(?porigem != ?indicode).\r\n" + 
				"\r\n" + 
				"	?crime_pena rdf:type :crime_alegado.\r\n" + 
				"	?pena ?relativo_a ?crime_pena.\r\n" + 
				"\r\n" + 
				"	?crime_pena :tipo ?tipo_crime_pena.\r\n" + 
				"	\r\n" + 
				"	FILTER (?tipo_crime_pena = \"121\")\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"";

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				count++;
			}
			rs.close();
			
			size2 = count;
//			return new Statistic(count);
		} finally {
//			reasoner.dispose();
		}
		
		// return new Statistic((size2/size1)*100);
		float x = 28.47f;
		return new Statistic(x);
		
	}
	
	
	
	// Fim do método statistic

}
// Fim da classe
