(function() {
    'use strict';

    angular
    .module('intersepApp')
    .controller('QuantitativaController', QuantitativaController);

    QuantitativaController.$inject = ['Quantitativa','Principal', '$scope', '$http'];

    function QuantitativaController(Quantitativa, Principal, $scope, $http) {

        var vm = this;

        vm.quantitativas = [];

        loadAll();

        function loadAll() {
            Quantitativa.query(function(result) {
                vm.quantitativas = result;
                vm.searchQuery = null;
            });
        }

        $scope.q1;

        gerarEstatistica();

        //http://dev.nemo.inf.ufes.br:8081/q1

        //http://localhost:8081/q1

        function gerarEstatistica(){
         $http({
            method: 'GET',
            url: ("http://localhost:8081/q1"),
            Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
        }).then(function successCallback(response) {
           console.log(response.data.value);

           $scope.q1 = response.data.value;

           var chartData = {
            type: "pie", 
            backgroundColor: "white",
            plot: {
              borderColor: "#2B313B",
              borderWidth: 1,
      // slice: 90,
      valueBox: {
        placement: 'out',
        text: '%t\n%npv%',
        fontFamily: "Open Sans"
    },
    tooltip:{
        fontSize: '18',
        fontFamily: "Open Sans",
        padding: "5 10",
        text: "%npv%"
    },
    animation:{
      effect: 2, 
      method: 5,
      speed: 500,
      sequence: 1
  }
},
plotarea: {
  margin: "0 0 0 0"  
},
series : [
{
    values : [$scope.q1],
    text: "Sim",
    backgroundColor: '#56af2f',
},
{
  values: [100 - $scope.q1],
  text: "Não",
  backgroundColor: '#64715e'
}
]
};

zingchart.render({ 
    id : 'chartDiv', 
    data : chartData, 
    height: 500, 
    width: 500 
});

                    // this callback will be called asynchronously
                    // when the response is available
                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                });

        $http({
            method: 'GET',
            url: ("http://localhost:8081/q2"),
            Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
        }).then(function successCallback(response) {
           console.log(response.data.value);

           $scope.q2 = response.data.value;

           var chartData = {
            type: "pie", 
            backgroundColor: "white",
            plot: {
              borderColor: "#2B313B",
              borderWidth: 1,
      // slice: 90,
      valueBox: {
        placement: 'out',
        text: '%t\n%npv%',
        fontFamily: "Open Sans"
    },
    tooltip:{
        fontSize: '18',
        fontFamily: "Open Sans",
        padding: "5 10",
        text: "%npv%"
    },
    animation:{
      effect: 2, 
      method: 5,
      speed: 500,
      sequence: 1
  }
},
plotarea: {
  margin: "0 0 0 0"  
},
series : [
{
    values : [$scope.q2],
    text: "Sim",
    backgroundColor: '#56af2f',
},
{
  values: [100 - $scope.q2],
  text: "Não",
  backgroundColor: '#64715e'
}
]
};

zingchart.render({ 
    id : 'chartDiv2', 
    data : chartData, 
    height: 500, 
    width: 500 
});

                // this callback will be called asynchronously
                // when the response is available
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });

        $http({
            method: 'GET',
            url: ("http://localhost:8081/q3"),
            Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTU0MDYxNDUzOX0.4bbZtX6NNwZS7skSDNlwkLJO-gmpBZzK-Ze66sdsgvhFtnv8ra0RVFlwWpm2FMbivAHKYDS7bNfxFT9PkW1b5w'
        }).then(function successCallback(response) {
           console.log(response.data.codigo);

           $scope.q3 = response.data.value;

           var chartData = {
            type: "pie", 
            backgroundColor: "white",
            plot: {
              borderColor: "#2B313B",
              borderWidth: 1,
      // slice: 90,
      valueBox: {
        placement: 'out',
        text: '%t\n%npv%',
        fontFamily: "Open Sans"
    },
    tooltip:{
        fontSize: '18',
        fontFamily: "Open Sans",
        padding: "5 10",
        text: "%npv%"
    },
    animation:{
      effect: 2, 
      method: 5,
      speed: 500,
      sequence: 1
  }
},
plotarea: {
  margin: "0 0 0 0"  
},
series : [
{
    values : [$scope.q3],
    text: "Sim",
    backgroundColor: '#56af2f',
},
{
  values: [100 - $scope.q3],
  text: "Não",
  backgroundColor: '#64715e'
}
]
};

zingchart.render({ 
    id : 'chartDiv3', 
    data : chartData, 
    height: 500, 
    width: 500 
});

                // this callback will be called asynchronously
                // when the response is available
            }, function errorCallback(response) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
            });

    }



}
})();
