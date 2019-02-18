(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QuantitativaDeleteController',QuantitativaDeleteController);

    QuantitativaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Quantitativa'];

    function QuantitativaDeleteController($uibModalInstance, entity, Quantitativa) {
        var vm = this;

        vm.quantitativa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Quantitativa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
