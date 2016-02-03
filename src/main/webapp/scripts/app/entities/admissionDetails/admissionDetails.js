'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('admissionDetails', {
                parent: 'entity',
                url: '/admissionDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.admissionDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/admissionDetails/admissionDetailss.html',
                        controller: 'AdmissionDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('admissionDetails');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('admissionDetails.detail', {
                parent: 'entity',
                url: '/admissionDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.admissionDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/admissionDetails/admissionDetails-detail.html',
                        controller: 'AdmissionDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('admissionDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AdmissionDetails', function($stateParams, AdmissionDetails) {
                        return AdmissionDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('admissionDetails.new', {
                parent: '',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/admissionDetails/newAdmissionForm.html',
                        controller: 'NewAdmissionDetailsDialogController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('admissionDetails');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'AdmissionDetails', function($stateParams, AdmissionDetails) {
                        return AdmissionDetails.get({  admissionNo: null,
                            admissionDate: null,
                            id: null});
                    }]
                }
      
            })
            .state('admissionDetails.edit', {
                parent: 'admissionDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/admissionDetails/admissionDetails-dialog.html',
                        controller: 'AdmissionDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AdmissionDetails', function(AdmissionDetails) {
                                return AdmissionDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('admissionDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('admissionDetails.delete', {
                parent: 'admissionDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/admissionDetails/admissionDetails-delete-dialog.html',
                        controller: 'AdmissionDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AdmissionDetails', function(AdmissionDetails) {
                                return AdmissionDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('admissionDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
