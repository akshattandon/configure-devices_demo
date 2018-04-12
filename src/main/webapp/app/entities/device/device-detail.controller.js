(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('DeviceDetailController', DeviceDetailController);

    DeviceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Device', 'Policy'];

    function DeviceDetailController($scope, $rootScope, $stateParams, previousState, entity, Device, Policy) {
        var vm = this;

        vm.device = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('configuredevicesApp:deviceUpdate', function(event, result) {
            vm.device = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
