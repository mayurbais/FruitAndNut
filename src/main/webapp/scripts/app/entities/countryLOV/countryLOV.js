'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('countryLOV', {
                parent: 'entity',
                url: '/countryLOVs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.countryLOV.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/countryLOV/countryLOVs.html',
                        controller: 'CountryLOVController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('countryLOV');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('countryLOV.detail', {
                parent: 'entity',
                url: '/countryLOV/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.countryLOV.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/countryLOV/countryLOV-detail.html',
                        controller: 'CountryLOVDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('countryLOV');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CountryLOV', function($stateParams, CountryLOV) {
                        return CountryLOV.get({id : $stateParams.id});
                    }]
                }
            })
            .state('countryLOV.new', {
                parent: 'countryLOV',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/countryLOV/countryLOV-dialog.html',
                        controller: 'CountryLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    value: null,
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('countryLOV', null, { reload: true });
                    }, function() {
                        $state.go('countryLOV');
                    })
                }]
            })
            .state('countryLOV.edit', {
                parent: 'countryLOV',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/countryLOV/countryLOV-dialog.html',
                        controller: 'CountryLOVDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CountryLOV', function(CountryLOV) {
                                return CountryLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('countryLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('countryLOV.delete', {
                parent: 'countryLOV',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/countryLOV/countryLOV-delete-dialog.html',
                        controller: 'CountryLOVDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CountryLOV', function(CountryLOV) {
                                return CountryLOV.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('countryLOV', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
