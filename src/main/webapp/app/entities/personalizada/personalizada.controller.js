(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('PersonalizadaController', PersonalizadaController);

    PersonalizadaController.$inject = ['Personalizada','$scope', '$http'];

    function PersonalizadaController(Personalizada,$scope,$http) {

        var vm = this;

        vm.personalizadas = [];

        $scope.retorno;

        $scope.query = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\n" +
        "SELECT ?tip WHERE {\n" + 
    "?tip rdf:type :tipificacao_do_crime.\n" +
    "}\n\n";

        function replaceAll(str, de, para){
            var pos = str.indexOf(de);
            while (pos > -1){
                str = str.replace(de, para);
                pos = str.indexOf(de);
            }
            return (str);
        }

        $scope.copiar_pro_editor = function(query){
            console.log(query.q);
            $scope.query = query.q;
        }

        $scope.sparql = function() {

            // var qjson = JSON.stringify($scope.query);
            // var jsonquery = JSON.parse(qjson);

            var formatada = encodeURIComponent($scope.query);
            // formatada = replaceAll(formatada, "#","%23");
            console.log(formatada);

            $http({
              method: 'GET',
              url: ("http://localhost:8081/sparql?q="+formatada),
              Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
          }).then(function successCallback(response) {

            console.log(response.data);

            $scope.retorno = response.data.resposta;

        }, function errorCallback(response) {

        });
      }


        loadAll();

        function loadAll() {
            Personalizada.query(function(result) {
                vm.personalizadas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
