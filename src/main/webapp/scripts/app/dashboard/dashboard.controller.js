'use strict';

angular.module('try1App')
    .controller('DashboardController', function ($scope, $state, Dashboard) {


        $scope.getSuccess = function(result){
        	$scope.students = result
        }
        
        $scope.errorSucess = function(result){
        	alert("in error");
        }
        
        $scope.loadAll = function() {
        	Dashboard.getMyChildren($scope.getSuccess,$scope.errorSucess);
        };
        
        $scope.loadAll();

        
        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.acedemicSession = {
                sessionStartDate: null,
                sessionEndDate: null,
                id: null
            };
        };
    });
