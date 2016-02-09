'use strict';

angular.module('try1App')
    .controller('DairyTemplateDetailController', function ($scope, $rootScope, $stateParams, entity, DairyTemplate, Course, Student, Teacher) {
        $scope.dairyTemplate = entity;
        $scope.load = function (id) {
            DairyTemplate.get({id: id}, function(result) {
                $scope.dairyTemplate = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:dairyTemplateUpdate', function(event, result) {
            $scope.dairyTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
