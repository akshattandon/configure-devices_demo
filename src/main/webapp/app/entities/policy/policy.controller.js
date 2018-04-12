(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('PolicyController', PolicyController);

    PolicyController.$inject = ['Policy'];

    function PolicyController(Policy) {

        var vm = this;

        vm.policies = [];

        loadAll();

        function loadAll() {
            Policy.query(function(result) {
                vm.policies = result;
                vm.searchQuery = null;
            });
        }
    }
})();
