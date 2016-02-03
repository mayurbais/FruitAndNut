'use strict';

angular.module('try1App')
    .controller('AdmissionDetailsController', function ($scope, $state, AdmissionDetails) {
    	
        $scope.admissionDetailss = [];
        $scope.loadAll = function() {
            AdmissionDetails.query(function(result) {
               $scope.admissionDetailss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.admissionDetails = {
                admissionNo: null,
                admissionDate: null,
                id: null
            };
        };
    });
