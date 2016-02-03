'use strict';

angular.module('try1App')
    .controller('AdmissionDetailsDetailController', function ($scope, $rootScope, $stateParams, entity, AdmissionDetails, Student, PrevSchoolInfo) {
        $scope.admissionDetails = entity;
        $scope.load = function (id) {
            AdmissionDetails.get({id: id}, function(result) {
                $scope.admissionDetails = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:admissionDetailsUpdate', function(event, result) {
            $scope.admissionDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
