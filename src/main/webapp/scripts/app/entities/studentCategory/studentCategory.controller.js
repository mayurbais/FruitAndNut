'use strict';

angular.module('try1App')
    .controller('StudentCategoryController', function ($scope, $state, StudentCategory) {

        $scope.studentCategorys = [];
        $scope.loadAll = function() {
            StudentCategory.query(function(result) {
               $scope.studentCategorys = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.studentCategory = {
                name: null,
                value: null,
                description: null,
                id: null
            };
        };
    });
