(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('DeviceStatusDeleteController',DeviceStatusDeleteController);

    DeviceStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'DeviceStatus'];

    function DeviceStatusDeleteController($uibModalInstance, entity, DeviceStatus) {
        var vm = this;

        vm.deviceStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DeviceStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
