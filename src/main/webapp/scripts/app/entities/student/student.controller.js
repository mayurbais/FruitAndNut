'use strict';

angular.module('try1App')
    .controller('StudentController', function ($scope, $state, Student) {

        $scope.students = [];
        $scope.loadAll = function() {
            Student.query(function(result) {
               $scope.students = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {
                rollNo: null,
                id: null
            };
        };
    });
