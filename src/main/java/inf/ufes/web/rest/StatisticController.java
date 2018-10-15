package inf.ufes.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unibz.inf.ontop.injection.OntopSQLOWLAPIConfiguration;
import it.unibz.inf.ontop.owlapi.OntopOWLFactory;
import it.unibz.inf.ontop.owlapi.OntopOWLReasoner;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLConnection;
import it.unibz.inf.ontop.owlapi.connection.OntopOWLStatement;
import it.unibz.inf.ontop.owlapi.resultset.OWLBindingSet;
import it.unibz.inf.ontop.owlapi.resultset.TupleOWLResultSet;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.reasoner.IllegalConfigurationException;
import org.semanticweb.owlapi.reasoner.ReasonerInternalException;

@RestController
public class StatisticController {

	private final String owlfile = "src/main/resources/ontologia/PCDV.owl";
	private final String obdafile = "src/main/resources/ontologia/PCDV.obda";
	private final String propertiesfile = "src/main/resources/ontologia/PCDV.properties";
	
	OntopOWLReasoner reasoner = null;
	OntopOWLFactory factory = null;
	OntopSQLOWLAPIConfiguration config = null;
	
	public StatisticController() {
		this.factory = OntopOWLFactory.defaultFactory();
		this.config = (OntopSQLOWLAPIConfiguration) OntopSQLOWLAPIConfiguration.defaultBuilder()
				.nativeOntopMappingFile(obdafile).ontologyFile(owlfile).propertyFile(propertiesfile).enableTestMode()
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
				"SELECT ?indi ?p ?pcpf ?indicode WHERE {\n"+
				"?indi rdf:type :indiciamento.\n"+
				"?p rdf:type :indiciado.\n"+
				"?indi :indicia ?p.\n"+
				"?p :cpf ?pcpf.\n"+
				"?p :cpf "+cpf+".\n"+
				"?indi :codigo ?indicode\n"+
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
			return new Indiciamento(i);
			
		} finally {
//			reasoner.dispose();
		}
	}

	@RequestMapping("/statistic")
	public Statistic statistic(@RequestParam(value = "iq") int iq)
			throws IllegalConfigurationException, ReasonerInternalException, OWLException {
		
		int size1 = -1;
		
		String sparqlQuery = "";

		switch (iq) {
		case 1:
			sparqlQuery = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
					"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
					"PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\n"+
					"SELECT ?indi ?tipo_indi ?penal ?tipo_crime_pena WHERE {\n"+
					"?indi rdf:type :indiciamento.\n"+
					"?crime rdf:type :crime_alegado.\n"+
					"?indi :relativo_a ?crime.\n"+
					"?indi :codigo ?indicode.\n"+
					"?crime :tipo ?tipo_indi.\n"+
					"?penal rdf:type :processo_execucao_penal.\n"+
					"?pena rdf:type :pena_imposta.\n"+
					"?pena :componente_de ?penal.\n"+
					"?penal :processo_origem ?indicode.\n"+
					"?crime_pena rdf:type :crime_alegado.\n"+
					"?pena ?relativo_a ?crime_pena.\n"+
					"?crime_pena :tipo ?tipo_crime_pena\n"+
					"}";
			// return new Statistic("Questão 1",0);
			break;

		default:
			// return null;
			break;
		}

		try (OntopOWLConnection conn = reasoner.getConnection(); OntopOWLStatement st = conn.createStatement()) {
			
			int count = 0;
			
			long t1 = System.currentTimeMillis();
			TupleOWLResultSet rs = st.executeSelectQuery(sparqlQuery);
			int columnSize = rs.getColumnCount();
			size1 = columnSize;
			String i;
			while (rs.hasNext()) {
				final OWLBindingSet bindingSet = rs.next();
				for (int idx = 1; idx <= columnSize; idx++) {
					OWLObject binding = bindingSet.getOWLObject(idx);
					System.out.print(ToStringRenderer.getInstance().getRendering(binding) + ", ");
//					i = ToStringRenderer.getInstance().getRendering(binding);
//					return new Statistic(i, (float) rs.getColumnCount());
					count++;
				}
				System.out.print("\n");
			}
			rs.close();
			long t2 = System.currentTimeMillis();
			String sqlQuery = st.getExecutableQuery(sparqlQuery).toString();
			return new Statistic("Q1",(float) count,"","");
		} finally {
//			reasoner.dispose();
		}

		
	}
	// Fim do método statistic

}
// Fim da classe
