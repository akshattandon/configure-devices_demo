(function() {
    'use strict';

    angular
        .module('configuredevicesApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('device-status', {
            parent: 'entity',
            url: '/device-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'configuredevicesApp.deviceStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-status/device-statuses.html',
                    controller: 'DeviceStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deviceStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('device-status-detail', {
            parent: 'device-status',
            url: '/device-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'configuredevicesApp.deviceStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/device-status/device-status-detail.html',
                    controller: 'DeviceStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('deviceStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DeviceStatus', function($stateParams, DeviceStatus) {
                    return DeviceStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'device-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('device-status-detail.edit', {
            parent: 'device-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-status/device-status-dialog.html',
                    controller: 'DeviceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeviceStatus', function(DeviceStatus) {
                            return DeviceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-status.new', {
            parent: 'device-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-status/device-status-dialog.html',
                    controller: 'DeviceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                details: null,
                                threshold: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('device-status', null, { reload: 'device-status' });
                }, function() {
                    $state.go('device-status');
                });
            }]
        })
        .state('device-status.edit', {
            parent: 'device-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-status/device-status-dialog.html',
                    controller: 'DeviceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DeviceStatus', function(DeviceStatus) {
                            return DeviceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-status', null, { reload: 'device-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('device-status.delete', {
            parent: 'device-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/device-status/device-status-delete-dialog.html',
                    controller: 'DeviceStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DeviceStatus', function(DeviceStatus) {
                            return DeviceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('device-status', null, { reload: 'device-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
