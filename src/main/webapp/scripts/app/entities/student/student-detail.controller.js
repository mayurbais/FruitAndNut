'use strict';

angular.module('try1App')
    .controller('StudentDetailController', function ($scope, $rootScope, $stateParams, entity, Student, StudentCategory, Course, Section, IrisUser) {
        $scope.student = entity;
        $scope.load = function (id) {
            Student.get({id: id}, function(result) {
                $scope.student = result;
            });
        };
        var unsubscribe = $rootScope.$on('try1App:studentUpdate', function(event, result) {
            $scope.student = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });

angular.module('try1App')
.controller('ChildDetailController', 
		 ['$scope', '$rootScope','$stateParams', 'entity',    'Student', 'StudentCategory','Course','Section','IrisUser','Events','CustomEvents',
		  	function ($scope, $rootScope, $stateParams, entity, Student, StudentCategory, Course, Section, IrisUser, Events,CustomEvents) {
    $scope.student = entity;
    $scope.load = function (id){
    	 Student.get({id: id}, function(result) {
             $scope.student = result;
         });
       
       
    };
    
   CustomEvents.getSectionEvents( {sectionId:$scope.student.section.id}, function(result) {
        $scope.eventss = result;
    });
    
    //$scope.load();
    var unsubscribe = $rootScope.$on('try1App:studentUpdate', function(event, result) {
        $scope.student = result;
    });
    $scope.$on('$destroy', unsubscribe);

}]);
