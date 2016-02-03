'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studentCategory', {
                parent: 'entity',
                url: '/studentCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.studentCategory.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentCategory/studentCategorys.html',
                        controller: 'StudentCategoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentCategory');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('studentCategory.detail', {
                parent: 'entity',
                url: '/studentCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.studentCategory.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentCategory/studentCategory-detail.html',
                        controller: 'StudentCategoryDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentCategory');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentCategory', function($stateParams, StudentCategory) {
                        return StudentCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('studentCategory.new', {
                parent: 'studentCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentCategory/studentCategory-dialog.html',
                        controller: 'StudentCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    value: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('studentCategory', null, { reload: true });
                    }, function() {
                        $state.go('studentCategory');
                    })
                }]
            })
            .state('studentCategory.edit', {
                parent: 'studentCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentCategory/studentCategory-dialog.html',
                        controller: 'StudentCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StudentCategory', function(StudentCategory) {
                                return StudentCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('studentCategory.delete', {
                parent: 'studentCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentCategory/studentCategory-delete-dialog.html',
                        controller: 'StudentCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StudentCategory', function(StudentCategory) {
                                return StudentCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
