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

	?crime_pena :tipo ?tipo_crime_pena.
	
	FILTER (?tipo_indi = "1"^^xsd:decimal).
	FILTER (?tipo_crime_pena = "121")

}

[QueryItem="Q2"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?pcpf ?indicode ?porigem WHERE {

	?indi rdf:type :indiciamento.
	?p rdf:type :indiciado.
	
	?indi :indicia ?p.
	
	?p :cpf ?pcpf.
	
	?p :nome ?pnome.

	?indi :codigo ?indicode.

	?indi :relativo_a ?crime.
	?crime :tipo ?tipo_indi.

	?penal rdf:type :processo_execucao_penal.
	?pena rdf:type :pena_imposta.
	?pena :componente_de ?penal.

	?penal :processo_origem ?porigem.
	?c rdf:type :condenado.
	?c :recebe ?penal.
	?c :cpf ?pcpf.

	FILTER (?tipo_indi = "1"^^xsd:decimal).
	FILTER(?porigem != ?indicode).
}

[QueryItem="Q3"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?pcpf ?indicode ?porigem WHERE {

	?indi rdf:type :indiciamento.
	?p rdf:type :indiciado.
	
	?indi :indicia ?p.
	
	?p :cpf ?pcpf.
	
	?p :nome ?pnome.

	?indi :codigo ?indicode.

	?indi :relativo_a ?crime.
	?crime :tipo ?tipo_indi.

	?penal rdf:type :processo_execucao_penal.
	?pena rdf:type :pena_imposta.
	?pena :componente_de ?penal.

	?penal :processo_origem ?porigem.
	?c rdf:type :condenado.
	?c :recebe ?penal.
	?c :cpf ?pcpf.

	FILTER (?tipo_indi = "1"^^xsd:decimal).
	FILTER(?porigem != ?indicode).

	?crime_pena rdf:type :crime_alegado.
	?pena ?relativo_a ?crime_pena.

	?crime_pena :tipo ?tipo_crime_pena.
	
	FILTER (?tipo_crime_pena = "121")
}

[QueryItem="BASICA"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>
SELECT ?indi WHERE {
	?indi rdf:type :indiciamento.
}

[QueryItem="Q22"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?penal{
	?penal rdf:type :processo_execucao_penal.
	?pena rdf:type :pena_imposta.
	?pena :componente_de ?penal.

	?penal :processo_origem ?porigem.
	?c rdf:type :condenado.
	?c :recebe ?penal.
	?c :cpf ?ccpf.

}

[QueryItem="Q4"]
PREFIX owl: <http://www.w3.org/2002/07/owl#>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>

SELECT ?pnome ?pcpf ?pcode WHERE {

	?indi rdf:type :indiciamento.
	?indi :codigo "47192185".
	
	?p rdf:type :indiciado.
	
	?indi :indicia ?p.
	
	?p :cpf ?pcpf.
	
	?p :nome ?pnome.

	?penal rdf:type :processo_execucao_penal.
	
	?c rdf:type :condenado.
	?c :recebe ?penal.
	?c :cpf ?pcpf.

	?penal :codigo ?pcode.

}
