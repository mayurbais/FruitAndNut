'use strict';

angular.module('try1App')
    .controller('ElectiveSubjectController', function ($scope, $state, ElectiveSubject) {

        $scope.electiveSubjects = [];
        $scope.loadAll = function() {
            ElectiveSubject.query(function(result) {
               $scope.electiveSubjects = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.electiveSubject = {
                name: null,
                description: null,
                code: null,
                id: null
            };
        };
    });
