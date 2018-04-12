(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function HomeController ($scope, Principal, LoginService, $state) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        
        $scope.map = { center: { latitude: 59.330009, longitude: 18.055628 }, zoom: 14 };
        $scope.markers = [];
        HipsterPoi.query(function(pois) {
                angular.forEach(pois, function(poi){
                    this.push(poi);
                }, $scope.markers);
            }
        );

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
