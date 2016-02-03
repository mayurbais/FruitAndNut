'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('section', {
                parent: 'entity',
                url: '/sections',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.section.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/section/sections.html',
                        controller: 'SectionController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('section');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('section.detail', {
                parent: 'entity',
                url: '/section/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.section.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/section/section-detail.html',
                        controller: 'SectionDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('section');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Section', function($stateParams, Section) {
                        return Section.get({id : $stateParams.id});
                    }]
                }
            })
            .state('section.new', {
                parent: 'section',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/section/section-dialog.html',
                        controller: 'SectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    code: null,
                                    strength: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('section', null, { reload: true });
                    }, function() {
                        $state.go('section');
                    })
                }]
            })
            .state('section.edit', {
                parent: 'section',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/section/section-dialog.html',
                        controller: 'SectionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Section', function(Section) {
                                return Section.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('section', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('section.delete', {
                parent: 'section',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/section/section-delete-dialog.html',
                        controller: 'SectionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Section', function(Section) {
                                return Section.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('section', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
