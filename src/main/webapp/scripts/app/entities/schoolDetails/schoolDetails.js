'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('schoolDetails', {
                parent: 'entity',
                url: '/schoolDetailss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.schoolDetails.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/schoolDetails/schoolDetailss.html',
                        controller: 'SchoolDetailsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('schoolDetails');
                        $translatePartialLoader.addPart('attendanceType');
                        $translatePartialLoader.addPart('days');
                        $translatePartialLoader.addPart('dateFormat');
                        $translatePartialLoader.addPart('gradingType');
                        $translatePartialLoader.addPart('institutionType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('schoolDetails.detail', {
                parent: 'entity',
                url: '/schoolDetails/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.schoolDetails.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/schoolDetails/schoolDetails-detail.html',
                        controller: 'SchoolDetailsDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('schoolDetails');
                        $translatePartialLoader.addPart('attendanceType');
                        $translatePartialLoader.addPart('days');
                        $translatePartialLoader.addPart('dateFormat');
                        $translatePartialLoader.addPart('gradingType');
                        $translatePartialLoader.addPart('institutionType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SchoolDetails', function($stateParams, SchoolDetails) {
                        return SchoolDetails.get({id : $stateParams.id});
                    }]
                }
            })
            .state('schoolDetails.new', {
                parent: 'schoolDetails',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/schoolDetails/schoolDetails-dialog.html',
                        controller: 'SchoolDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    schoolName: null,
                                    address: null,
                                    phoneNumber: null,
                                    attendanceType: null,
                                    startDayOfTheWeek: null,
                                    dateFormat: null,
                                    financialStartDate: null,
                                    financialEndDate: null,
                                    logo: null,
                                    gradingSystem: null,
                                    enableAutoIncreamentOfAdmissionNo: null,
                                    enableNewsCommentsModeration: null,
                                    enableSibling: null,
                                    enablePasswordChangeAtFirstLogin: null,
                                    enableRollNumberForStudent: null,
                                    institutionType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('schoolDetails', null, { reload: true });
                    }, function() {
                        $state.go('schoolDetails');
                    })
                }]
            })
            .state('schoolDetails.edit', {
                parent: 'schoolDetails',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/schoolDetails/schoolDetails-dialog.html',
                        controller: 'SchoolDetailsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SchoolDetails', function(SchoolDetails) {
                                return SchoolDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('schoolDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('schoolDetails.delete', {
                parent: 'schoolDetails',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/schoolDetails/schoolDetails-delete-dialog.html',
                        controller: 'SchoolDetailsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SchoolDetails', function(SchoolDetails) {
                                return SchoolDetails.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('schoolDetails', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
