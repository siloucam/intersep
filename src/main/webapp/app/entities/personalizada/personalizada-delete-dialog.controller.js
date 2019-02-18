(function() {
    'use strict';

    angular
        .module('intersepApp')
        .controller('PersonalizadaDeleteController',PersonalizadaDeleteController);

    PersonalizadaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Personalizada'];

    function PersonalizadaDeleteController($uibModalInstance, entity, Personalizada) {
        var vm = this;

        vm.personalizada = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Personalizada.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
