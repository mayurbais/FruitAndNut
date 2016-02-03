'use strict';

angular.module('try1App')
    .controller('CourseController', function ($scope, $state, Course) {

        $scope.courses = [];
        $scope.loadAll = function() {
            Course.query(function(result) {
               $scope.courses = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.course = {
                name: null,
                code: null,
                enableElectiveSelection: null,
                description: null,
                courseGroup: null,
                id: null
            };
        };
    });
