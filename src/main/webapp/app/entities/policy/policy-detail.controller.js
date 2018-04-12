(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('PolicyDetailController', PolicyDetailController);

    PolicyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Policy', 'Device'];

    function PolicyDetailController($scope, $rootScope, $stateParams, previousState, entity, Policy, Device) {
        var vm = this;

        vm.policy = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('configuredevicesApp:policyUpdate', function(event, result) {
            vm.policy = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
