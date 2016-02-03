'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subjectExamResult', {
                parent: 'entity',
                url: '/subjectExamResults',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.subjectExamResult.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subjectExamResult/subjectExamResults.html',
                        controller: 'SubjectExamResultController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subjectExamResult');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('subjectExamResult.detail', {
                parent: 'entity',
                url: '/subjectExamResult/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.subjectExamResult.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subjectExamResult/subjectExamResult-detail.html',
                        controller: 'SubjectExamResultDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('subjectExamResult');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SubjectExamResult', function($stateParams, SubjectExamResult) {
                        return SubjectExamResult.get({id : $stateParams.id});
                    }]
                }
            })
            .state('subjectExamResult.new', {
                parent: 'subjectExamResult',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectExamResult/subjectExamResult-dialog.html',
                        controller: 'SubjectExamResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    studentId: null,
                                    marksObtained: null,
                                    grade: null,
                                    isPassed: null,
                                    isAbsent: null,
                                    remark: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('subjectExamResult', null, { reload: true });
                    }, function() {
                        $state.go('subjectExamResult');
                    })
                }]
            })
            .state('subjectExamResult.edit', {
                parent: 'subjectExamResult',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectExamResult/subjectExamResult-dialog.html',
                        controller: 'SubjectExamResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SubjectExamResult', function(SubjectExamResult) {
                                return SubjectExamResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subjectExamResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('subjectExamResult.delete', {
                parent: 'subjectExamResult',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/subjectExamResult/subjectExamResult-delete-dialog.html',
                        controller: 'SubjectExamResultDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SubjectExamResult', function(SubjectExamResult) {
                                return SubjectExamResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subjectExamResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
