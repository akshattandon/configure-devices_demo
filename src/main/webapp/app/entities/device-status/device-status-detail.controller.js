(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('DeviceStatusDetailController', DeviceStatusDetailController);

    DeviceStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'DeviceStatus', 'Device'];

    function DeviceStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, DeviceStatus, Device) {
        var vm = this;

        vm.deviceStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('configuredevicesApp:deviceStatusUpdate', function(event, result) {
            vm.deviceStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
