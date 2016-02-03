'use strict';

angular.module('try1App')
    .controller('ExamResultDetailController', function ($scope, $rootScope, $stateParams, entity, ExamResult, Exam) {
        $scope.examResult = entity;
        $scope.load = function (id) {
            ExamResult.get({id: id}, function(result) {
                $scope.examResult = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:examResultUpdate', function(event, result) {
            $scope.examResult = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
