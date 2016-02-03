'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('customAdmissionDetails', {
                parent: 'entity',
                url: '/customAdmissionDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.customAdmissionDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customAdmissionDetails/customAdmissionDetailss.html',
                        controller: 'CustomAdmissionDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customAdmissionDetails');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('customAdmissionDetails.detail', {
                parent: 'entity',
                url: '/customAdmissionDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.customAdmissionDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/customAdmissionDetails/customAdmissionDetails-detail.html',
                        controller: 'CustomAdmissionDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('customAdmissionDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'CustomAdmissionDetails', function($stateParams, CustomAdmissionDetails) {
                        return CustomAdmissionDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('customAdmissionDetails.new', {
                parent: 'customAdmissionDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customAdmissionDetails/customAdmissionDetails-dialog.html',
                        controller: 'CustomAdmissionDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    isActive: null,
                                    isMandatory: null,
                                    inputMethod: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('customAdmissionDetails', null, { reload: true });
                    }, function() {
                        $state.go('customAdmissionDetails');
                    })
                }]
            })
            .state('customAdmissionDetails.edit', {
                parent: 'customAdmissionDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customAdmissionDetails/customAdmissionDetails-dialog.html',
                        controller: 'CustomAdmissionDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['CustomAdmissionDetails', function(CustomAdmissionDetails) {
                                return CustomAdmissionDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customAdmissionDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('customAdmissionDetails.delete', {
                parent: 'customAdmissionDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/customAdmissionDetails/customAdmissionDetails-delete-dialog.html',
                        controller: 'CustomAdmissionDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['CustomAdmissionDetails', function(CustomAdmissionDetails) {
                                return CustomAdmissionDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('customAdmissionDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
