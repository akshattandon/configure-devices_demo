(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('LocationDialogController', LocationDialogController);

    LocationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Location', 'Device'];

    function LocationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Location, Device) {
        var vm = this;

        vm.location = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query({filter: 'location-is-null'});
        $q.all([vm.location.$promise, vm.devices.$promise]).then(function() {
            if (!vm.location.device || !vm.location.device.id) {
                return $q.reject();
            }
            return Device.get({id : vm.location.device.id}).$promise;
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
            if (vm.location.id !== null) {
                Location.update(vm.location, onSaveSuccess, onSaveError);
            } else {
                Location.save(vm.location, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('configuredevicesApp:locationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
