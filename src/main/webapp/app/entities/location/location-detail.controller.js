(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('LocationDetailController', LocationDetailController);

    LocationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Location', 'Device'];

    function LocationDetailController($scope, $rootScope, $stateParams, previousState, entity, Location, Device) {
        var vm = this;

        vm.location = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('configuredevicesApp:locationUpdate', function(event, result) {
            vm.location = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
