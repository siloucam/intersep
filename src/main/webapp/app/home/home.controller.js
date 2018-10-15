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
        
        $scope.cpf = "20311295866";
        $scope.indiciamento;
        $scope.penal;
        
        $scope.buscar = function() {
//            $scope.cpf = "141";
        	
        	$http({
        		  method: 'GET',
        		  url: ("http://localhost:8080/indiciamentos_cpf?cpf='"+$scope.cpf+"'"),
        		  Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
        		}).then(function successCallback(response) {
        			console.log(response.data.codigo);
        			
        			$scope.indiciamento = response.data.codigo;
        			
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
        		  url: ("http://localhost:8080/statistic?iq=1"),
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
