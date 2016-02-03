'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prevSchoolInfo', {
                parent: 'entity',
                url: '/prevSchoolInfos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.prevSchoolInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prevSchoolInfo/prevSchoolInfos.html',
                        controller: 'PrevSchoolInfoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prevSchoolInfo');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('prevSchoolInfo.detail', {
                parent: 'entity',
                url: '/prevSchoolInfo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.prevSchoolInfo.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prevSchoolInfo/prevSchoolInfo-detail.html',
                        controller: 'PrevSchoolInfoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('prevSchoolInfo');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PrevSchoolInfo', function($stateParams, PrevSchoolInfo) {
                        return PrevSchoolInfo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prevSchoolInfo.new', {
                parent: 'prevSchoolInfo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/prevSchoolInfo/prevSchoolInfo-dialog.html',
                        controller: 'PrevSchoolInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    schoolName: null,
                                    grade: null,
                                    remarkBy: null,
                                    remark: null,
                                    contactOfRemark: null,
                                    reasonForChange: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('prevSchoolInfo', null, { reload: true });
                    }, function() {
                        $state.go('prevSchoolInfo');
                    })
                }]
            })
            .state('prevSchoolInfo.edit', {
                parent: 'prevSchoolInfo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/prevSchoolInfo/prevSchoolInfo-dialog.html',
                        controller: 'PrevSchoolInfoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PrevSchoolInfo', function(PrevSchoolInfo) {
                                return PrevSchoolInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prevSchoolInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('prevSchoolInfo.delete', {
                parent: 'prevSchoolInfo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/prevSchoolInfo/prevSchoolInfo-delete-dialog.html',
                        controller: 'PrevSchoolInfoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['PrevSchoolInfo', function(PrevSchoolInfo) {
                                return PrevSchoolInfo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prevSchoolInfo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
