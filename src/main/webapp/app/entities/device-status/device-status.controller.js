(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('DeviceStatusController', DeviceStatusController);

    DeviceStatusController.$inject = ['DeviceStatus'];

    function DeviceStatusController(DeviceStatus) {

        var vm = this;

        vm.deviceStatuses = [];

        loadAll();

        function loadAll() {
            DeviceStatus.query(function(result) {
                vm.deviceStatuses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
