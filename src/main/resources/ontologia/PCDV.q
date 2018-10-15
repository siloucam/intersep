[QueryItem="Q1"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?indi ?tipo_indi ?penal ?tipo_crime_pena WHERE {

	?indi rdf:type :indiciamento.
	?crime rdf:type :crime_alegado.
	?indi :relativo_a ?crime.
	?indi :codigo ?indicode.
	?crime :tipo ?tipo_indi.
	
	?penal rdf:type :processo_execucao_penal.
	?pena rdf:type :pena_imposta.
	?pena :componente_de ?penal.

	?penal :processo_origem ?indicode.
	
	?crime_pena rdf:type :crime_alegado.
	?pena ?relativo_a ?crime_pena.

	?crime_pena :tipo ?tipo_crime_pena
}

[QueryItem="Q2"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?indi ?p ?pcpf ?indicode WHERE {

	?indi rdf:type :indiciamento.
	?p rdf:type :indiciado.
	
	?indi :indicia ?p.
	
	?p :cpf ?pcpf.
	
	?p :cpf "20311295866".

	?indi :codigo ?indicode
}

[QueryItem="Q3"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?penal ?p ?pcpf ?penalcode WHERE {

	?penal rdf:type :processo_execucao_penal.
	?p rdf:type :condenado.
	
	?p :recebe ?penal.
	
	?p :cpf ?pcpf.
	
	?p :cpf "20311295866".

	?penal :codigo ?penalcode.
}
