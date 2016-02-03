'use strict';

angular.module('try1App')
    .controller('SubjectController', function ($scope, $state, Subject) {

        $scope.subjects = [];
        $scope.loadAll = function() {
            Subject.query(function(result) {
               $scope.subjects = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.subject = {
                name: null,
                description: null,
                code: null,
                noExam: null,
                id: null
            };
        };
    });
