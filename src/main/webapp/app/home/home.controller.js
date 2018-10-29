(function() {
    'use strict';

    angular
    .module('intersepHipsterApp')
    .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http'];

    function HomeController ($scope, Principal, LoginService, $state, $http) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        $scope.q1;
        
        gerarEstatistica();
        
        $scope.cpf = "14859954844";
        $scope.indiciamento;
        $scope.indiciado;
        $scope.penal;
        $scope.retorno;

        $scope.query = "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" +
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
        "PREFIX : <http://www.semanticweb.org/nemo/ontologies/pcdv#>\n\n"
        ;

        function replaceAll(str, de, para){
            var pos = str.indexOf(de);
            while (pos > -1){
                str = str.replace(de, para);
                pos = str.indexOf(de);
            }
            return (str);
        }

        $scope.sparql = function() {

            // var qjson = JSON.stringify($scope.query);
            // var jsonquery = JSON.parse(qjson);

            var formatada = encodeURIComponent($scope.query);
            // formatada = replaceAll(formatada, "#","%23");
            console.log(formatada);

            $http({
              method: 'GET',
              url: ("http://localhost:8080/sparql?q="+formatada),
              Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
          }).then(function successCallback(response) {

            console.log(response.data);

            $scope.retorno = response.data.resposta;

        }, function errorCallback(response) {

        });
      }

      $scope.buscar = function() {
//            $scope.cpf = "141";

$http({
    method: 'GET',
    url: ("http://localhost:8080/indiciamentos_cpf?cpf='"+$scope.cpf+"'"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
 console.log(response.data.codigo);

 $scope.indiciamento = response.data.codigo;
 $scope.indiciado = response.data.indiciado;

        		    // this callback will be called asynchronously
        		    // when the response is available
                }, function errorCallback(response) {
        		    // called asynchronously if an error occurs
        		    // or server returns response with an error status.
                });

$http({
  method: 'GET',
  url: ("http://localhost:8080/penais_cpf?cpf='"+$scope.cpf+"'"),
  Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
   console.log(response.data.codigo);

   $scope.penal = response.data.codigo;

      		    // this callback will be called asynchronously
      		    // when the response is available
              }, function errorCallback(response) {
      		    // called asynchronously if an error occurs
      		    // or server returns response with an error status.
              });


};


function gerarEstatistica(){
   $http({
    method: 'GET',
    url: ("http://localhost:8080/q1"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
 console.log(response.data.codigo);

 $scope.q1 = response.data.value;

        		    // this callback will be called asynchronously
        		    // when the response is available
                }, function errorCallback(response) {
        		    // called asynchronously if an error occurs
        		    // or server returns response with an error status.
                });

   $http({
    method: 'GET',
    url: ("http://localhost:8080/q2"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
 console.log(response.data.codigo);

 $scope.q2 = response.data.value;

                // this callback will be called asynchronously
                // when the response is available
                }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                });

$http({
    method: 'GET',
    url: ("http://localhost:8080/q3"),
    Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
}).then(function successCallback(response) {
 console.log(response.data.codigo);

 $scope.q3 = response.data.value;

                // this callback will be called asynchronously
                // when the response is available
                }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                });

}

function getAccount() {
    Principal.identity().then(function(account) {
        vm.account = account;
        vm.isAuthenticated = Principal.isAuthenticated;
    });
}
function register () {
    $state.go('register');
}
}
})();
