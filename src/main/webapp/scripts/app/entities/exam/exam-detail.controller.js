'use strict';

angular.module('try1App')
    .controller('ExamDetailController', function ($scope, $rootScope, $stateParams, entity, Exam, Course, Section, ExamSubjects) {
        $scope.exam = entity;
        $scope.load = function (id) {
            Exam.get({id: id}, function(result) {
                $scope.exam = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:examUpdate', function(event, result) {
            $scope.exam = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
