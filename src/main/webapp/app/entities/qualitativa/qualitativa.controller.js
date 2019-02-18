(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QualitativaController', QualitativaController);

    QualitativaController.$inject = ['Qualitativa', '$http','$scope'];

    function QualitativaController(Qualitativa, $http,$scope) {

        var vm = this;

        vm.qualitativas = [];

        loadAll();

        function loadAll() {
            Qualitativa.query(function(result) {
                vm.qualitativas = result;
                vm.searchQuery = null;
            });
        }

        $scope.buscoucpf = false;
        $scope.buscaprocesso = false;

        $scope.buscacpf = "60166536824";
        $scope.indiciamento;
        $scope.indiciado;
        $scope.penal;

        $scope.cpf;
        $scope.nome;
        $scope.condenacao;

        $scope.codigoprocesso = "06701068";


        $scope.buscarprocesso = function(){

$http({
    method: 'GET',
    url: ("http://localhost:8081/processo?codigo='"+$scope.codigoprocesso+"'"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {

    $scope.buscoucpf = false;
    $scope.buscaprocesso = true;

    console.log(response.data);

    // "^^xsd:string

    var str = response.data.nome;
    str = str.replace("\"^^xsd:string","");
    str = str.replace("\""," ");
    $scope.nome = str;

    str = response.data.cpf;
    str = str.replace("\"^^xsd:string","");
    str = str.replace("\""," ");
    $scope.cpf = str;

    var str = response.data.condenacao;
    str = str.replace("\"^^xsd:string","");
    str = str.replace("\""," ");
    $scope.condenacao = str;

                    // this callback will be called asynchronously
                    // when the response is available
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });
}

$scope.buscar = function() {

$http({
    method: 'GET',
    url: ("http://localhost:8081/indiciamentos_cpf?cpf='"+$scope.buscacpf+"'"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {

    console.log(response.data);

    $scope.buscoucpf = true;
    $scope.buscaprocesso = false;

    var str2 = response.data.codigo;
    str2 = str2.replace("\"^^xsd:string","");
    str2 = str2.replace("\""," ");
    $scope.indiciamento = str2;

    // "^^xsd:string

    var str = response.data.indiciado;
    str = str.replace("\"^^xsd:string","");
    str = str.replace("\""," ");
    $scope.indiciado = str;




                    // this callback will be called asynchronously
                    // when the response is available
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });

$http({
  method: 'GET',
  url: ("http://localhost:8081/penais_cpf?cpf='"+$scope.buscacpf+"'"),
  Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
   console.log(response.data.codigo);


    var str = response.data.codigo;
    str = str.replace("\"^^xsd:string","");
    str = str.replace("\""," ");
    $scope.penal = str;


                // this callback will be called asynchronously
                // when the response is available
              }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
              });


};

    }
})();
