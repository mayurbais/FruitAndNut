'use strict';

angular.module('try1App')
    .controller('NoticeDetailController', function ($scope, $rootScope, $stateParams, entity, Notice, IrisUser) {
        $scope.notice = entity;
        $scope.load = function (id) {
            Notice.get({id: id}, function(result) {
                $scope.notice = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:noticeUpdate', function(event, result) {
            $scope.notice = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
