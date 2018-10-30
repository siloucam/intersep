(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QualitativasDetailController', QualitativasDetailController);

    QualitativasDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Qualitativas'];

    function QualitativasDetailController($scope, $rootScope, $stateParams, previousState, entity, Qualitativas) {
        var vm = this;

        vm.qualitativas = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('intersepHipsterApp:qualitativasUpdate', function(event, result) {
            vm.qualitativas = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
