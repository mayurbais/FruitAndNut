'use strict';

angular.module('try1App')
    .controller('IrisUserController', function ($scope, $state, IrisUser) {

        $scope.irisUsers = [];
        $scope.loadAll = function() {
            IrisUser.query(function(result) {
               $scope.irisUsers = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.irisUser = {
                firstName: null,
                lastName: null,
                middleName: null,
                dateOfBirth: null,
                gender: null,
                bloodGroup: null,
                birthPlace: null,
                religion: null,
                photo: null,
                phone: null,
                id: null
            };
        };
    });
