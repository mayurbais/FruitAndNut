'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('endDate', {
                parent: 'entity',
                url: '/endDates',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.endDate.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/endDate/endDates.html',
                        controller: 'EndDateController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('endDate');
                        $translatePartialLoader.addPart('days');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('endDate.detail', {
                parent: 'entity',
                url: '/endDate/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.endDate.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/endDate/endDate-detail.html',
                        controller: 'EndDateDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('endDate');
                        $translatePartialLoader.addPart('days');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'EndDate', function($stateParams, EndDate) {
                        return EndDate.get({id : $stateParams.id});
                    }]
                }
            })
            .state('endDate.new', {
                parent: 'endDate',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/endDate/endDate-dialog.html',
                        controller: 'EndDateDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    day: null,
                                    startDate: null,
                                    endDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('endDate', null, { reload: true });
                    }, function() {
                        $state.go('endDate');
                    })
                }]
            })
            .state('endDate.edit', {
                parent: 'endDate',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/endDate/endDate-dialog.html',
                        controller: 'EndDateDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['EndDate', function(EndDate) {
                                return EndDate.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('endDate', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('endDate.delete', {
                parent: 'endDate',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/endDate/endDate-delete-dialog.html',
                        controller: 'EndDateDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['EndDate', function(EndDate) {
                                return EndDate.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('endDate', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
