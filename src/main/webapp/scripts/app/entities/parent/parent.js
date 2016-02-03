'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('parent', {
                parent: 'entity',
                url: '/parents',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.parent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parent/parents.html',
                        controller: 'ParentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parent');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('parent.detail', {
                parent: 'entity',
                url: '/parent/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.parent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/parent/parent-detail.html',
                        controller: 'ParentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('parent');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Parent', function($stateParams, Parent) {
                        return Parent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('parent.new', {
                parent: 'parent',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/parent/parent-dialog.html',
                        controller: 'ParentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    occupation: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('parent', null, { reload: true });
                    }, function() {
                        $state.go('parent');
                    })
                }]
            })
            .state('parent.edit', {
                parent: 'parent',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/parent/parent-dialog.html',
                        controller: 'ParentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Parent', function(Parent) {
                                return Parent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('parent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('parent.delete', {
                parent: 'parent',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/parent/parent-delete-dialog.html',
                        controller: 'ParentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Parent', function(Parent) {
                                return Parent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('parent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
