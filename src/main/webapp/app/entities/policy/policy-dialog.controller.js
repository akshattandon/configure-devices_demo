(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('PolicyDialogController', PolicyDialogController);

    PolicyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Policy', 'Device'];

    function PolicyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Policy, Device) {
        var vm = this;

        vm.policy = entity;
        vm.clear = clear;
        vm.save = save;
        vm.devices = Device.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.policy.id !== null) {
                Policy.update(vm.policy, onSaveSuccess, onSaveError);
            } else {
                Policy.save(vm.policy, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('configuredevicesApp:policyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
