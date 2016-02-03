'use strict';

angular.module('try1App').controller('NoticeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Notice', 'IrisUser',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Notice, IrisUser) {

        $scope.notice = entity;
        $scope.irisusers = IrisUser.query({filter: 'notice-is-null'});
        $q.all([$scope.notice.$promise, $scope.irisusers.$promise]).then(function() {
            if (!$scope.notice.irisUser || !$scope.notice.irisUser.id) {
                return $q.reject();
            }
            return IrisUser.get({id : $scope.notice.irisUser.id}).$promise;
        }).then(function(irisUser) {
            $scope.irisusers.push(irisUser);
        });
        $scope.load = function(id) {
            Notice.get({id : id}, function(result) {
                $scope.notice = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('try1App:noticeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.notice.id != null) {
                Notice.update($scope.notice, onSaveSuccess, onSaveError);
            } else {
                Notice.save($scope.notice, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForSendDate = {};

        $scope.datePickerForSendDate.status = {
            opened: false
        };

        $scope.datePickerForSendDateOpen = function($event) {
            $scope.datePickerForSendDate.status.opened = true;
        };
}]);
