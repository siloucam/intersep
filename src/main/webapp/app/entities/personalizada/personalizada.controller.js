(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('PersonalizadaController', PersonalizadaController);

    PersonalizadaController.$inject = ['Personalizada'];

    function PersonalizadaController(Personalizada) {

        var vm = this;

        vm.personalizadas = [];

        loadAll();

        function loadAll() {
            Personalizada.query(function(result) {
                vm.personalizadas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
