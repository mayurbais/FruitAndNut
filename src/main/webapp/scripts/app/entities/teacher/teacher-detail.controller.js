'use strict';

angular.module('try1App')
    .controller('TeacherDetailController', function ($scope, $rootScope, $stateParams, entity, Teacher, Subject, Employee) {
        $scope.teacher = entity;
        $scope.load = function (id) {
            Teacher.get({id: id}, function(result) {
                $scope.teacher = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:teacherUpdate', function(event, result) {
            $scope.teacher = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
