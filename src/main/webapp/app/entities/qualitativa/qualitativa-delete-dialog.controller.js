(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('QualitativaDeleteController',QualitativaDeleteController);

    QualitativaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Qualitativa'];

    function QualitativaDeleteController($uibModalInstance, entity, Qualitativa) {
        var vm = this;

        vm.qualitativa = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Qualitativa.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
