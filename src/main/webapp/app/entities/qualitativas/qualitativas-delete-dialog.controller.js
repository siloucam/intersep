(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QualitativasDeleteController',QualitativasDeleteController);

    QualitativasDeleteController.$inject = ['$uibModalInstance', 'entity', 'Qualitativas'];

    function QualitativasDeleteController($uibModalInstance, entity, Qualitativas) {
        var vm = this;

        vm.qualitativas = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Qualitativas.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
