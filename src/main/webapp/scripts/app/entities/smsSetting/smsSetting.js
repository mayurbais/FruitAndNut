'use strict';

angular.module('try1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('smsSetting', {
                parent: 'entity',
                url: '/smsSettings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.smsSetting.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/smsSetting/smsSettings.html',
                        controller: 'SmsSettingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsSetting');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('smsSetting.detail', {
                parent: 'entity',
                url: '/smsSetting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'try1App.smsSetting.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/smsSetting/smsSetting-detail.html',
                        controller: 'SmsSettingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('smsSetting');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'SmsSetting', function($stateParams, SmsSetting) {
                        return SmsSetting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('smsSetting.new', {
                parent: 'smsSetting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/smsSetting/smsSetting-dialog.html',
                        controller: 'SmsSettingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    code: null,
                                    isEnabled: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('smsSetting', null, { reload: true });
                    }, function() {
                        $state.go('smsSetting');
                    })
                }]
            })
            .state('smsSetting.edit', {
                parent: 'smsSetting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/smsSetting/smsSetting-dialog.html',
                        controller: 'SmsSettingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SmsSetting', function(SmsSetting) {
                                return SmsSetting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsSetting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('smsSetting.delete', {
                parent: 'smsSetting',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/smsSetting/smsSetting-delete-dialog.html',
                        controller: 'SmsSettingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SmsSetting', function(SmsSetting) {
                                return SmsSetting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('smsSetting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
