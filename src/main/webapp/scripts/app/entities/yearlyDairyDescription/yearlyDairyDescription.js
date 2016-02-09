'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('yearlyDairyDescription', {
                parent: 'entity',
                url: '/yearlyDairyDescriptions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.yearlyDairyDescription.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yearlyDairyDescription/yearlyDairyDescriptions.html',
                        controller: 'YearlyDairyDescriptionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('yearlyDairyDescription');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('yearlyDairyDescription.detail', {
                parent: 'entity',
                url: '/yearlyDairyDescription/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.yearlyDairyDescription.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/yearlyDairyDescription/yearlyDairyDescription-detail.html',
                        controller: 'YearlyDairyDescriptionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('yearlyDairyDescription');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'YearlyDairyDescription', function($stateParams, YearlyDairyDescription) {
                        return YearlyDairyDescription.get({id : $stateParams.id});
                    }]
                }
            })
            .state('yearlyDairyDescription.new', {
                parent: 'yearlyDairyDescription',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yearlyDairyDescription/yearlyDairyDescription-dialog.html',
                        controller: 'YearlyDairyDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    year: null,
                                    theme: null,
                                    summerDressCode: null,
                                    winterDressCode: null,
                                    isEnabled: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('yearlyDairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('yearlyDairyDescription');
                    })
                }]
            })
            .state('yearlyDairyDescription.edit', {
                parent: 'yearlyDairyDescription',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yearlyDairyDescription/yearlyDairyDescription-dialog.html',
                        controller: 'YearlyDairyDescriptionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['YearlyDairyDescription', function(YearlyDairyDescription) {
                                return YearlyDairyDescription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yearlyDairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('yearlyDairyDescription.delete', {
                parent: 'yearlyDairyDescription',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/yearlyDairyDescription/yearlyDairyDescription-delete-dialog.html',
                        controller: 'YearlyDairyDescriptionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['YearlyDairyDescription', function(YearlyDairyDescription) {
                                return YearlyDairyDescription.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('yearlyDairyDescription', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
