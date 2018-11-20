(function() {
    'use strict';

    angular
        .module('intersepHipsterApp')
        .controller('QuantitativasController', QuantitativasController);

    QuantitativasController.$inject = ['Quantitativas'];

    function QuantitativasController(Quantitativas) {

        var vm = this;

        vm.quantitativas = [];

        loadAll();

        function loadAll() {
            Quantitativas.query(function(result) {
                vm.quantitativas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
