'use strict';

angular.module('try1App')
    .controller('TeacherController', function ($scope, $state, Teacher) {

        $scope.teachers = [];
        $scope.loadAll = function() {
            Teacher.query(function(result) {
               $scope.teachers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.teacher = {
                experties: null,
                courseGroup: null,
                id: null
            };
        };
    });
