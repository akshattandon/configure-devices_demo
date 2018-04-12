(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('DeviceStatusDialogController', DeviceStatusDialogController);

    DeviceStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DeviceStatus', 'Device'];

    function DeviceStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, DeviceStatus, Device) {
        var vm = this;

        vm.deviceStatus = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query({filter: 'devicestatus-is-null'});
        $q.all([vm.deviceStatus.$promise, vm.devices.$promise]).then(function() {
            if (!vm.deviceStatus.device || !vm.deviceStatus.device.id) {
                return $q.reject();
            }
            return Device.get({id : vm.deviceStatus.device.id}).$promise;
        }).then(function(device) {
            vm.devices.push(device);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.deviceStatus.id !== null) {
                DeviceStatus.update(vm.deviceStatus, onSaveSuccess, onSaveError);
            } else {
                DeviceStatus.save(vm.deviceStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('configuredevicesApp:deviceStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
