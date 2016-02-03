'use strict';

angular.module('try1App')
    .controller('SessionDetailController', function ($scope, $rootScope, $stateParams, entity, Session, Employee, Section, TimeTable) {
        $scope.session = entity;
        $scope.load = function (id) {
            Session.get({id: id}, function(result) {
                $scope.session = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:sessionUpdate', function(event, result) {
            $scope.session = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
