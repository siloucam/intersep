(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QuantitativasDeleteController',QuantitativasDeleteController);

    QuantitativasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Quantitativas'];

    function QuantitativasDeleteController($uibModalInstance, entity, Quantitativas) {
        var vm = this;

        vm.quantitativas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Quantitativas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
