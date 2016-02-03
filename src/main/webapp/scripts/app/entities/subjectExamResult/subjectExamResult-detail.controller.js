'use strict';

angular.module('try1App')
    .controller('SubjectExamResultDetailController', function ($scope, $rootScope, $stateParams, entity, SubjectExamResult, ExamSubjects) {
        $scope.subjectExamResult = entity;
        $scope.load = function (id) {
            SubjectExamResult.get({id: id}, function(result) {
                $scope.subjectExamResult = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:subjectExamResultUpdate', function(event, result) {
            $scope.subjectExamResult = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
