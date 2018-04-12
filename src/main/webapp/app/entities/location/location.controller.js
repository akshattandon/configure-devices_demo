(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('LocationController', LocationController);

    LocationController.$inject = ['Location'];

    function LocationController(Location) {

        var vm = this;

        vm.locations = [];

        loadAll();

        function loadAll() {
            Location.query(function(result) {
                vm.locations = result;
                vm.searchQuery = null;
            });
        }
    }
})();
