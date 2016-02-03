'use strict';

angular.module('try1App')
    .controller('ElectiveSubjectDetailController', function ($scope, $rootScope, $stateParams, entity, ElectiveSubject, Course) {
        $scope.electiveSubject = entity;
        $scope.load = function (id) {
            ElectiveSubject.get({id: id}, function(result) {
                $scope.electiveSubject = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:electiveSubjectUpdate', function(event, result) {
            $scope.electiveSubject = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
