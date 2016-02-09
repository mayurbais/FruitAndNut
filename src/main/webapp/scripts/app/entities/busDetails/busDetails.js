'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('busDetails', {
                parent: 'entity',
                url: '/busDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.busDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/busDetails/busDetailss.html',
                        controller: 'BusDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('busDetails');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('busDetails.detail', {
                parent: 'entity',
                url: '/busDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.busDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/busDetails/busDetails-detail.html',
                        controller: 'BusDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('busDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'BusDetails', function($stateParams, BusDetails) {
                        return BusDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('busDetails.new', {
                parent: 'busDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/busDetails/busDetails-dialog.html',
                        controller: 'BusDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    busNo: null,
                                    route: null,
                                    timing: null,
                                    driverName: null,
                                    driverContactNo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('busDetails', null, { reload: true });
                    }, function() {
                        $state.go('busDetails');
                    })
                }]
            })
            .state('busDetails.edit', {
                parent: 'busDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/busDetails/busDetails-dialog.html',
                        controller: 'BusDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['BusDetails', function(BusDetails) {
                                return BusDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('busDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('busDetails.delete', {
                parent: 'busDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/busDetails/busDetails-delete-dialog.html',
                        controller: 'BusDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['BusDetails', function(BusDetails) {
                                return BusDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('busDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
